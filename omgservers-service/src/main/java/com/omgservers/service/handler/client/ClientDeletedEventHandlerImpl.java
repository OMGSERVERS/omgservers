package com.omgservers.service.handler.client;

import com.omgservers.model.client.ClientModel;
import com.omgservers.model.clientMatchmakerRef.ClientMatchmakerRefModel;
import com.omgservers.model.clientMessage.ClientMessageModel;
import com.omgservers.model.clientRuntimeRef.ClientRuntimeRefModel;
import com.omgservers.model.dto.client.DeleteClientMessagesRequest;
import com.omgservers.model.dto.client.DeleteClientMessagesResponse;
import com.omgservers.model.dto.client.GetClientRequest;
import com.omgservers.model.dto.client.GetClientResponse;
import com.omgservers.model.dto.client.ViewClientMatchmakerRefsRequest;
import com.omgservers.model.dto.client.ViewClientMatchmakerRefsResponse;
import com.omgservers.model.dto.client.ViewClientMessagesRequest;
import com.omgservers.model.dto.client.ViewClientMessagesResponse;
import com.omgservers.model.dto.client.ViewClientRuntimeRefsRequest;
import com.omgservers.model.dto.client.ViewClientRuntimeRefsResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerCommandRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerCommandResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeClientRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeClientResponse;
import com.omgservers.model.dto.runtime.FindRuntimeClientRequest;
import com.omgservers.model.dto.runtime.FindRuntimeClientResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.module.ClientDeletedEventBodyModel;
import com.omgservers.model.matchmakerCommand.body.DeleteClientMatchmakerCommandBodyModel;
import com.omgservers.model.runtimeClient.RuntimeClientModel;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.MatchmakerCommandModelFactory;
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

                    final var idempotencyKey = event.getIdempotencyKey();

                    return handleClientMatchmakerRefs(clientId, idempotencyKey)
                            .flatMap(voidItem -> handleClientRuntimeRefs(clientId))
                            .flatMap(voidItem -> handleClientMessages(clientId));
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
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATION)) {
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
                            return findAndDeleteRuntimeClient(runtimeId, clientId);
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

    Uni<Void> findAndDeleteRuntimeClient(final Long runtimeId, final Long clientId) {
        return findRuntimeClient(runtimeId, clientId)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithNull()
                .onItem().ifNotNull().transformToUni(runtimeClient ->
                        deleteRuntimeClient(runtimeId, runtimeClient.getId()))
                .replaceWithVoid();
    }

    Uni<RuntimeClientModel> findRuntimeClient(final Long runtimeId, final Long clientId) {
        final var request = new FindRuntimeClientRequest(runtimeId, clientId);
        return runtimeModule.getRuntimeService().findRuntimeClient(request)
                .map(FindRuntimeClientResponse::getRuntimeClient);
    }

    Uni<Boolean> deleteRuntimeClient(final Long runtimeId, final Long id) {
        final var request = new DeleteRuntimeClientRequest(runtimeId, id);
        return runtimeModule.getRuntimeService().deleteRuntimeClient(request)
                .map(DeleteRuntimeClientResponse::getDeleted);
    }

    Uni<Void> handleClientMessages(final Long clientId) {
        return viewClientMessages(clientId)
                .flatMap(clientMessages -> deleteClientMessages(clientId, clientMessages))
                .replaceWithVoid();
    }

    Uni<List<ClientMessageModel>> viewClientMessages(final Long clientId) {
        final var request = new ViewClientMessagesRequest(clientId);
        return clientModule.getClientService().viewClientMessages(request)
                .map(ViewClientMessagesResponse::getClientMessages);
    }

    Uni<Boolean> deleteClientMessages(final Long clientId, List<ClientMessageModel> messages) {
        final var ids = messages.stream().map(ClientMessageModel::getId).toList();
        final var request = new DeleteClientMessagesRequest(clientId, ids);
        return clientModule.getClientService().deleteClientMessages(request)
                .map(DeleteClientMessagesResponse::getDeleted);
    }
}

