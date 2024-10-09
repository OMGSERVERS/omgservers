package com.omgservers.service.handler.impl.client;

import com.omgservers.schema.model.client.ClientModel;
import com.omgservers.schema.model.clientMatchmakerRef.ClientMatchmakerRefModel;
import com.omgservers.schema.model.clientRuntimeRef.ClientRuntimeRefModel;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.matchmakerCommand.body.DeleteClientMatchmakerCommandBodyModel;
import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
import com.omgservers.schema.module.client.GetClientRequest;
import com.omgservers.schema.module.client.GetClientResponse;
import com.omgservers.schema.module.client.ViewClientMatchmakerRefsRequest;
import com.omgservers.schema.module.client.ViewClientMatchmakerRefsResponse;
import com.omgservers.schema.module.client.ViewClientRuntimeRefsRequest;
import com.omgservers.schema.module.client.ViewClientRuntimeRefsResponse;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerCommandRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerCommandResponse;
import com.omgservers.schema.module.runtime.DeleteRuntimeAssignmentRequest;
import com.omgservers.schema.module.runtime.DeleteRuntimeAssignmentResponse;
import com.omgservers.schema.module.runtime.FindRuntimeAssignmentRequest;
import com.omgservers.schema.module.runtime.FindRuntimeAssignmentResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.client.ClientDeletedEventBodyModel;
import com.omgservers.service.exception.ServerSideBaseException;
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

        final var idempotencyKey = event.getId().toString();

        return getClient(clientId)
                .flatMap(client -> {
                    log.info("Client was deleted, id={}", clientId);

                    // Remain client messages because users can come back to get disconnection reason
                    return handleClientMatchmakerRefs(clientId, idempotencyKey)
                            .flatMap(voidItem -> handleClientRuntimeRefs(clientId));
                })
                .replaceWithVoid();
    }

    Uni<ClientModel> getClient(final Long clientId) {
        final var request = new GetClientRequest(clientId);
        return clientModule.getService().getClient(request)
                .map(GetClientResponse::getClient);
    }

    Uni<Void> handleClientMatchmakerRefs(final Long clientId, final String idempotencyKey) {
        return viewClientMatchmakerRefs(clientId)
                .flatMap(clientMatchmakerRefs -> Multi.createFrom().iterable(clientMatchmakerRefs)
                        .onItem().transformToUniAndConcatenate(clientMatchmakerRef -> {
                            final var matchmakerId = clientMatchmakerRef.getMatchmakerId();
                            // TODO: findAndDeleteMatchmakerAssignment

                            return syncDeleteClientMatchmakerCommand(matchmakerId, clientId, idempotencyKey);
                        })
                        .collect().asList()
                        .replaceWithVoid());
    }

    Uni<List<ClientMatchmakerRefModel>> viewClientMatchmakerRefs(final Long clientId) {
        final var request = new ViewClientMatchmakerRefsRequest(clientId);
        return clientModule.getService().viewClientMatchmakerRefs(request)
                .map(ViewClientMatchmakerRefsResponse::getClientMatchmakerRefs);
    }

    Uni<Boolean> syncDeleteClientMatchmakerCommand(final Long matchmakerId,
                                                   final Long clientId,
                                                   final String idempotencyKey) {
        final var commandBody = new DeleteClientMatchmakerCommandBodyModel(clientId);
        final var commandIdempotencyKey = idempotencyKey + "/" + matchmakerId;
        final var commandModel = matchmakerCommandModelFactory.create(matchmakerId,
                commandBody,
                commandIdempotencyKey);
        final var request = new SyncMatchmakerCommandRequest(commandModel);
        return matchmakerModule.getService().syncMatchmakerCommandWithIdempotency(request)
                .map(SyncMatchmakerCommandResponse::getCreated)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.PARENT_NOT_FOUND)) {
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
        return clientModule.getService().viewClientRuntimeRefs(request)
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
        return runtimeModule.getService().findRuntimeAssignment(request)
                .map(FindRuntimeAssignmentResponse::getRuntimeAssignment);
    }

    Uni<Boolean> deleteRuntimeAssignment(final Long runtimeId, final Long id) {
        final var request = new DeleteRuntimeAssignmentRequest(runtimeId, id);
        return runtimeModule.getService().deleteRuntimeAssignment(request)
                .map(DeleteRuntimeAssignmentResponse::getDeleted);
    }
}

