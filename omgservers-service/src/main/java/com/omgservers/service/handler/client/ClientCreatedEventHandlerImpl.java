package com.omgservers.service.handler.client;

import com.omgservers.model.client.ClientModel;
import com.omgservers.model.dto.client.GetClientRequest;
import com.omgservers.model.dto.client.GetClientResponse;
import com.omgservers.model.dto.client.SyncClientMessageRequest;
import com.omgservers.model.dto.client.SyncClientMessageResponse;
import com.omgservers.model.dto.lobby.GetLobbyRequest;
import com.omgservers.model.dto.lobby.GetLobbyResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeClientRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeClientResponse;
import com.omgservers.model.dto.tenant.ViewVersionLobbyRefsRequest;
import com.omgservers.model.dto.tenant.ViewVersionLobbyRefsResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.ClientCreatedEventBodyModel;
import com.omgservers.model.lobby.LobbyModel;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.WelcomeMessageBodyModel;
import com.omgservers.model.versionLobbyRef.VersionLobbyRefModel;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.ClientMessageModelFactory;
import com.omgservers.service.factory.RuntimeClientModelFactory;
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
public class ClientCreatedEventHandlerImpl implements EventHandler {

    final RuntimeModule runtimeModule;
    final TenantModule tenantModule;
    final ClientModule clientModule;
    final LobbyModule lobbyModule;

    final ClientMessageModelFactory clientMessageModelFactory;
    final RuntimeClientModelFactory runtimeClientModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.CLIENT_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (ClientCreatedEventBodyModel) event.getBody();
        final var clientId = body.getId();

        return getClient(clientId)
                .flatMap(client -> {
                    final var tenantId = client.getTenantId();
                    final var versionId = client.getVersionId();

                    log.info("Client was created, id={}, version={}/{}",
                            clientId, tenantId, versionId);

                    return syncWelcomeMessage(client)
                            .flatMap(created -> selectVersionLobbyRef(tenantId, versionId))
                            .flatMap(versionLobbyRef -> getLobby(versionLobbyRef.getLobbyId()))
                            .flatMap(lobby -> {
                                final var runtimeId = lobby.getRuntimeId();
                                return syncRuntimeClient(runtimeId, clientId);
                            });
                })
                .replaceWithVoid();
    }

    Uni<ClientModel> getClient(final Long clientId) {
        final var request = new GetClientRequest(clientId);
        return clientModule.getClientService().getClient(request)
                .map(GetClientResponse::getClient);
    }

    Uni<Boolean> syncWelcomeMessage(final ClientModel client) {
        final var clientId = client.getId();
        final var tenantId = client.getTenantId();
        final var versionId = client.getVersionId();
        final var messageBody = new WelcomeMessageBodyModel(tenantId, versionId);
        final var clientMessage = clientMessageModelFactory.create(clientId,
                MessageQualifierEnum.WELCOME_MESSAGE,
                messageBody);
        final var request = new SyncClientMessageRequest(clientMessage);
        return clientModule.getClientService().syncClientMessage(request)
                .map(SyncClientMessageResponse::getCreated);
    }

    Uni<VersionLobbyRefModel> selectVersionLobbyRef(final Long tenantId, final Long versionId) {
        return viewVersionLobbyRefs(tenantId, versionId)
                .map(refs -> {
                    if (refs.isEmpty()) {
                        throw new ServerSideConflictException(
                                String.format("lobby was not selected, version=%d/%d", tenantId, versionId));
                    } else {
                        final var randomRefIndex = ThreadLocalRandom.current().nextInt(refs.size()) % refs.size();
                        final var randomLobbyRef = refs.get(randomRefIndex);
                        return randomLobbyRef;
                    }
                });
    }

    Uni<List<VersionLobbyRefModel>> viewVersionLobbyRefs(final Long tenantId, final Long versionId) {
        final var request = new ViewVersionLobbyRefsRequest(tenantId, versionId);
        return tenantModule.getVersionService().viewVersionLobbyRefs(request)
                .map(ViewVersionLobbyRefsResponse::getVersionLobbyRefs);
    }

    Uni<LobbyModel> getLobby(final Long lobbyId) {
        final var request = new GetLobbyRequest(lobbyId);
        return lobbyModule.getLobbyService().getLobby(request)
                .map(GetLobbyResponse::getLobby);
    }

    Uni<Boolean> syncRuntimeClient(final Long runtimeId, final Long clientId) {
        final var runtimeClient = runtimeClientModelFactory.create(runtimeId, clientId);
        final var request = new SyncRuntimeClientRequest(runtimeClient);
        return runtimeModule.getRuntimeService().syncRuntimeClient(request)
                .map(SyncRuntimeClientResponse::getCreated);
    }
}

