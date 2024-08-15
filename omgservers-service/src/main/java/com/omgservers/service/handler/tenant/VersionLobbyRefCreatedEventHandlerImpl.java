package com.omgservers.service.handler.tenant;

import com.omgservers.schema.module.tenant.DeleteVersionLobbyRequestRequest;
import com.omgservers.schema.module.tenant.DeleteVersionLobbyRequestResponse;
import com.omgservers.schema.module.tenant.FindVersionLobbyRequestRequest;
import com.omgservers.schema.module.tenant.FindVersionLobbyRequestResponse;
import com.omgservers.schema.module.tenant.GetVersionLobbyRefRequest;
import com.omgservers.schema.module.tenant.GetVersionLobbyRefResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.VersionLobbyRefCreatedEventBodyModel;
import com.omgservers.schema.model.versionLobbyRef.VersionLobbyRefModel;
import com.omgservers.schema.model.versionLobbyRequest.VersionLobbyRequestModel;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.lobby.LobbyModule;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class VersionLobbyRefCreatedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;
    final LobbyModule lobbyModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.VERSION_LOBBY_REF_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (VersionLobbyRefCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getVersionLobbyRef(tenantId, id)
                .flatMap(versionLobbyRef -> {
                    final var versionId = versionLobbyRef.getVersionId();
                    final var lobbyId = versionLobbyRef.getLobbyId();
                    log.info("Version lobby ref was created, id={}, version={}/{}, lobbyId={}",
                            versionLobbyRef.getId(),
                            tenantId,
                            versionId,
                            lobbyId);

                    // TODO: update lobby state status
                    return findAndDeleteVersionLobbyRequest(tenantId, versionId, lobbyId);
                })
                .replaceWithVoid();
    }

    Uni<VersionLobbyRefModel> getVersionLobbyRef(final Long tenantId, final Long id) {
        final var request = new GetVersionLobbyRefRequest(tenantId, id);
        return tenantModule.getVersionService().getVersionLobbyRef(request)
                .map(GetVersionLobbyRefResponse::getVersionLobbyRef);
    }

    Uni<Boolean> findAndDeleteVersionLobbyRequest(final Long tenantId,
                                                  final Long versionId,
                                                  final Long lobbyId) {
        return findVersionLobbyRequest(tenantId, versionId, lobbyId)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithNull()
                .onItem().ifNotNull().transformToUni(versionLobbyRequest ->
                        deleteVersionLobbyRequest(tenantId, versionLobbyRequest.getId()));
    }

    Uni<VersionLobbyRequestModel> findVersionLobbyRequest(final Long tenantId,
                                                          final Long versionId,
                                                          final Long lobbyId) {
        final var request = new FindVersionLobbyRequestRequest(tenantId, versionId, lobbyId);
        return tenantModule.getVersionService().findVersionLobbyRequest(request)
                .map(FindVersionLobbyRequestResponse::getVersionLobbyRequest);
    }

    Uni<Boolean> deleteVersionLobbyRequest(final Long tenantId, final Long id) {
        final var request = new DeleteVersionLobbyRequestRequest(tenantId, id);
        return tenantModule.getVersionService().deleteVersionLobbyRequest(request)
                .map(DeleteVersionLobbyRequestResponse::getDeleted);
    }
}
