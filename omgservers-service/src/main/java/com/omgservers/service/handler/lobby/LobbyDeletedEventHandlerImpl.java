package com.omgservers.service.handler.lobby;

import com.omgservers.schema.module.lobby.GetLobbyRequest;
import com.omgservers.schema.module.lobby.GetLobbyResponse;
import com.omgservers.schema.module.runtime.DeleteRuntimeRequest;
import com.omgservers.schema.module.runtime.DeleteRuntimeResponse;
import com.omgservers.schema.module.tenant.DeleteVersionLobbyRefRequest;
import com.omgservers.schema.module.tenant.DeleteVersionLobbyRefResponse;
import com.omgservers.schema.module.tenant.FindVersionLobbyRefRequest;
import com.omgservers.schema.module.tenant.FindVersionLobbyRefResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.lobby.LobbyDeletedEventBodyModel;
import com.omgservers.schema.model.lobby.LobbyModel;
import com.omgservers.schema.model.versionLobbyRef.VersionLobbyRefModel;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.lobby.LobbyModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class LobbyDeletedEventHandlerImpl implements EventHandler {

    final RuntimeModule runtimeModule;
    final TenantModule tenantModule;
    final LobbyModule lobbyModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.LOBBY_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (LobbyDeletedEventBodyModel) event.getBody();
        final var lobbyId = body.getId();

        return getLobby(lobbyId)
                .flatMap(lobby -> {
                    log.info("Lobby was deleted, id={}", lobbyId);

                    return findAndDeleteVersionLobbyRef(lobby)
                            .flatMap(voidItem -> handleLobbyRuntime(lobby));
                })
                .replaceWithVoid();
    }

    Uni<LobbyModel> getLobby(final Long id) {
        final var request = new GetLobbyRequest(id);
        return lobbyModule.getLobbyService().getLobby(request)
                .map(GetLobbyResponse::getLobby);
    }

    Uni<Void> findAndDeleteVersionLobbyRef(LobbyModel lobby) {
        final var tenantId = lobby.getTenantId();
        final var versionId = lobby.getVersionId();
        final var lobbyId = lobby.getId();
        return findVersionLobbyRef(tenantId, versionId, lobbyId)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithNull()
                .onItem().ifNotNull().transformToUni(this::deleteVersionLobbyRef)
                .replaceWithVoid();
    }

    Uni<VersionLobbyRefModel> findVersionLobbyRef(final Long tenantId,
                                                  final Long versionId,
                                                  final Long lobbyId) {
        final var request = new FindVersionLobbyRefRequest(tenantId, versionId, lobbyId);
        return tenantModule.getVersionService().findVersionLobbyRef(request)
                .map(FindVersionLobbyRefResponse::getVersionLobbyRef);
    }

    Uni<Boolean> deleteVersionLobbyRef(VersionLobbyRefModel versionLobbyRef) {
        final var tenantId = versionLobbyRef.getTenantId();
        final var id = versionLobbyRef.getId();
        final var request = new DeleteVersionLobbyRefRequest(tenantId, id);
        return tenantModule.getVersionService().deleteVersionLobbyRef(request)
                .map(DeleteVersionLobbyRefResponse::getDeleted);
    }

    Uni<Void> handleLobbyRuntime(final LobbyModel lobby) {
        return deleteRuntime(lobby.getRuntimeId())
                .replaceWithVoid();
    }

    Uni<Boolean> deleteRuntime(final Long runtimeId) {
        final var request = new DeleteRuntimeRequest(runtimeId);
        return runtimeModule.getRuntimeService().deleteRuntime(request)
                .map(DeleteRuntimeResponse::getDeleted);
    }
}
