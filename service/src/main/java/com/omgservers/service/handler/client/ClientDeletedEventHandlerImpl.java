package com.omgservers.service.handler.client;

import com.omgservers.model.client.ClientModel;
import com.omgservers.model.clientMatchmakerRef.ClientMatchmakerRefModel;
import com.omgservers.model.clientRuntimeRef.ClientRuntimeRefModel;
import com.omgservers.model.dto.client.GetClientRequest;
import com.omgservers.model.dto.client.GetClientResponse;
import com.omgservers.model.dto.client.ViewClientMatchmakerRefsRequest;
import com.omgservers.model.dto.client.ViewClientMatchmakerRefsResponse;
import com.omgservers.model.dto.client.ViewClientRuntimeRefsRequest;
import com.omgservers.model.dto.client.ViewClientRuntimeRefsResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerCommandRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerCommandResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeAssignmentRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeAssignmentResponse;
import com.omgservers.model.dto.runtime.FindRuntimeAssignmentRequest;
import com.omgservers.model.dto.runtime.FindRuntimeAssignmentResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.module.client.ClientDeletedEventBodyModel;
import com.omgservers.model.matchmakerCommand.body.DeleteClientMatchmakerCommandBodyModel;
import com.omgservers.model.runtimeAssignment.RuntimeAssignmentModel;
import com.omgservers.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.matchmaker.MatchmakerCommandModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.client.ClientModule;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.user.UserModule;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ClientDeletedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final RuntimeModule runtimeModule;
    final ClientModule clientModule;
    final UserModule userModule;

    final MatchmakerCommandModelFactory matchmakerCommandModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.CLIENT_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (ClientDeletedEventBodyModel) event.getBody();
        final var clientId = body.getId();

        return getClient(clientId)
                .flatMap(client -> {
                    log.info("Client was deleted, clientId={}", clientId);

                    final var idempotencyKey = event.getId().toString();

                    // Remain client messages because users can come back to get disconnection reason
                    return handleClientMatchmakerRefs(clientId, idempotencyKey)
                            .flatMap(voidItem -> handleClientRuntimeRefs(clientId));
                })
                .replaceWithVoid();
    }

    Uni<ClientModel> getClient(final Long clientId) {
        final var request = new GetClientRequest(clientId);
        return clientModule.getClientService().getClient(request)
                .map(GetClientResponse::getClient);
    }

    Uni<Void> handleClientMatchmakerRefs(final Long clientId, final String idempotencyKey) {
        return viewClientMatchmakerRefs(clientId)
                .flatMap(clientMatchmakerRefs -> Multi.createFrom().iterable(clientMatchmakerRefs)
                        .onItem().transformToUniAndConcatenate(clientMatchmakerRef -> {
                            final var matchmakerId = clientMatchmakerRef.getMatchmakerId();
                            // TODO: findAndDeleteMatchmakerAssignment

                            return syncDeleteClientMatchmakerCommand(matchmakerId,
                                    clientId,
                                    idempotencyKey + "/" + matchmakerId);
                        })
                        .collect().asList()
                        .replaceWithVoid());
    }

    Uni<List<ClientMatchmakerRefModel>> viewClientMatchmakerRefs(final Long clientId) {
        final var request = new ViewClientMatchmakerRefsRequest(clientId);
        return clientModule.getClientService().viewClientMatchmakerRefs(request)
                .map(ViewClientMatchmakerRefsResponse::getClientMatchmakerRefs);
    }

    Uni<Boolean> syncDeleteClientMatchmakerCommand(final Long matchmakerId,
                                                   final Long clientId,
                                                   final String idempotencyKey) {
        final var commandBody = new DeleteClientMatchmakerCommandBodyModel(clientId);
        final var commandModel = matchmakerCommandModelFactory.create(matchmakerId,
                commandBody,
                idempotencyKey);
        final var request = new SyncMatchmakerCommandRequest(commandModel);
        return matchmakerModule.getMatchmakerService().syncMatchmakerCommand(request)
                .map(SyncMatchmakerCommandResponse::getCreated)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.PARENT_NOT_FOUND)) {
                            return Uni.createFrom().item(Boolean.FALSE);
                        }
                    }

                    return Uni.createFrom().failure(t);
                })
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.warn("Idempotency was violated, object={}, {}", commandModel, t.getMessage());
                            return Uni.createFrom().item(Boolean.FALSE);
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    Uni<Void> handleClientRuntimeRefs(final Long clientId) {
        return viewClientRuntimeRefs(clientId)
                .flatMap(clientRuntimeRefs -> Multi.createFrom().iterable(clientRuntimeRefs)
                        .onItem().transformToUniAndConcatenate(clientRuntimeRef -> {
                            final var runtimeId = clientRuntimeRef.getRuntimeId();
                            return findAndDeleteRuntimeAssignment(runtimeId, clientId);
                        })
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    Uni<List<ClientRuntimeRefModel>> viewClientRuntimeRefs(final Long clientId) {
        final var request = new ViewClientRuntimeRefsRequest(clientId);
        return clientModule.getClientService().viewClientRuntimeRefs(request)
                .map(ViewClientRuntimeRefsResponse::getClientRuntimeRefs);
    }

    Uni<Void> findAndDeleteRuntimeAssignment(final Long runtimeId, final Long clientId) {
        return findRuntimeAssignment(runtimeId, clientId)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithNull()
                .onItem().ifNotNull().transformToUni(runtimeAssignment ->
                        deleteRuntimeAssignment(runtimeId, runtimeAssignment.getId()))
                .replaceWithVoid();
    }

    Uni<RuntimeAssignmentModel> findRuntimeAssignment(final Long runtimeId, final Long clientId) {
        final var request = new FindRuntimeAssignmentRequest(runtimeId, clientId);
        return runtimeModule.getRuntimeService().findRuntimeAssignment(request)
                .map(FindRuntimeAssignmentResponse::getRuntimeAssignment);
    }

    Uni<Boolean> deleteRuntimeAssignment(final Long runtimeId, final Long id) {
        final var request = new DeleteRuntimeAssignmentRequest(runtimeId, id);
        return runtimeModule.getRuntimeService().deleteRuntimeAssignment(request)
                .map(DeleteRuntimeAssignmentResponse::getDeleted);
    }
}

