package com.omgservers.service.handler.impl.internal;

import com.omgservers.schema.model.clientMessage.ClientMessageModel;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.lobby.LobbyModel;
import com.omgservers.schema.model.message.MessageQualifierEnum;
import com.omgservers.schema.model.message.body.DisconnectionReasonEnum;
import com.omgservers.schema.model.message.body.DisconnectionReasonMessageBodyModel;
import com.omgservers.schema.model.tenantLobbyRef.TenantLobbyRefModel;
import com.omgservers.schema.module.client.DeleteClientRequest;
import com.omgservers.schema.module.client.DeleteClientResponse;
import com.omgservers.schema.module.client.SyncClientMessageRequest;
import com.omgservers.schema.module.client.SyncClientMessageResponse;
import com.omgservers.schema.module.lobby.GetLobbyRequest;
import com.omgservers.schema.module.lobby.GetLobbyResponse;
import com.omgservers.schema.module.runtime.SyncRuntimeAssignmentRequest;
import com.omgservers.schema.module.runtime.SyncRuntimeAssignmentResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRef.ViewTenantLobbyRefsRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRef.ViewTenantLobbyRefsResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.internal.LobbyAssignmentRequestedEventBodyModel;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.client.ClientMessageModelFactory;
import com.omgservers.service.factory.runtime.RuntimeAssignmentModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.client.ClientModule;
import com.omgservers.service.module.lobby.LobbyModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class LobbyAssignmentRequestedEventHandlerImpl implements EventHandler {

    final RuntimeModule runtimeModule;
    final TenantModule tenantModule;
    final ClientModule clientModule;
    final LobbyModule lobbyModule;

    final RuntimeAssignmentModelFactory runtimeAssignmentModelFactory;
    final ClientMessageModelFactory clientMessageModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.LOBBY_ASSIGNMENT_REQUESTED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (LobbyAssignmentRequestedEventBodyModel) event.getBody();
        final var clientId = body.getClientId();
        final var tenantId = body.getTenantId();
        final var deploymentId = body.getDeploymentId();

        final var idempotencyKey = event.getId().toString();

        return selectTenantLobbyRef(tenantId, deploymentId)
                .flatMap(tenantLobbyRef -> {
                    final var lobbyId = tenantLobbyRef.getLobbyId();
                    return getLobby(lobbyId)
                            .flatMap(lobby -> {
                                final var runtimeId = lobby.getRuntimeId();
                                return syncRuntimeAssignment(runtimeId, clientId, idempotencyKey)
                                        .replaceWithVoid();
                            });
                })
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithUni(t -> {
                    log.warn("Lobby assignment failed, clientId={}, {}:{}",
                            clientId,
                            t.getClass().getSimpleName(),
                            t.getMessage());
                    return syncDisconnectionMessage(clientId, idempotencyKey)
                            .flatMap(created -> deleteClient(clientId))
                            .replaceWithVoid();
                });
    }

    Uni<TenantLobbyRefModel> selectTenantLobbyRef(final Long tenantId, final Long deploymentId) {
        return viewTenantLobbyRefs(tenantId, deploymentId)
                .map(refs -> {
                    if (refs.isEmpty()) {
                        throw new ServerSideNotFoundException(ExceptionQualifierEnum.LOBBY_NOT_FOUND,
                                String.format("lobby was not selected, tenantDeployment=%d/%d", tenantId, deploymentId));
                    } else {
                        final var randomRefIndex = ThreadLocalRandom.current().nextInt(refs.size()) % refs.size();
                        final var randomLobbyRef = refs.get(randomRefIndex);
                        return randomLobbyRef;
                    }
                });
    }

    Uni<List<TenantLobbyRefModel>> viewTenantLobbyRefs(final Long tenantId, final Long deploymentId) {
        final var request = new ViewTenantLobbyRefsRequest(tenantId, deploymentId);
        return tenantModule.getTenantService().viewTenantLobbyRefs(request)
                .map(ViewTenantLobbyRefsResponse::getTenantLobbyRefs);
    }

    Uni<LobbyModel> getLobby(final Long lobbyId) {
        final var request = new GetLobbyRequest(lobbyId);
        return lobbyModule.getLobbyService().getLobby(request)
                .map(GetLobbyResponse::getLobby);
    }

    Uni<Boolean> syncRuntimeAssignment(final Long runtimeId,
                                       final Long clientId,
                                       final String idempotencyKey) {
        final var runtimeAssignment = runtimeAssignmentModelFactory.create(runtimeId,
                clientId,
                idempotencyKey);
        final var request = new SyncRuntimeAssignmentRequest(runtimeAssignment);
        return runtimeModule.getRuntimeService().syncRuntimeAssignment(request)
                .map(SyncRuntimeAssignmentResponse::getCreated)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.warn("Idempotency was violated, object={}, {}", runtimeAssignment, t.getMessage());
                            return Uni.createFrom().item(Boolean.FALSE);
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    Uni<Boolean> syncDisconnectionMessage(final Long clientId, final String idempotencyKey) {
        final var messageBody = new DisconnectionReasonMessageBodyModel(DisconnectionReasonEnum.INTERNAL_FAILURE);
        final var disconnectionMessage = clientMessageModelFactory.create(clientId,
                MessageQualifierEnum.DISCONNECTION_REASON_MESSAGE,
                messageBody,
                idempotencyKey);
        return syncClientMessage(disconnectionMessage);
    }

    Uni<Boolean> syncClientMessage(final ClientMessageModel clientMessage) {
        final var request = new SyncClientMessageRequest(clientMessage);
        return clientModule.getClientService().syncClientMessageWithIdempotency(request)
                .map(SyncClientMessageResponse::getCreated);
    }

    Uni<Boolean> deleteClient(final Long clientId) {
        final var request = new DeleteClientRequest(clientId);
        return clientModule.getClientService().deleteClient(request)
                .map(DeleteClientResponse::getDeleted);
    }
}
