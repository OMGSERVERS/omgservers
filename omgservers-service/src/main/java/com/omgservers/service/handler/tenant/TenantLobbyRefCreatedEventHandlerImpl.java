package com.omgservers.service.handler.tenant;

import com.omgservers.schema.module.tenant.tenantLobbyRequest.DeleteTenantLobbyRequestRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.DeleteTenantLobbyRequestResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.FindTenantLobbyRequestRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.FindTenantLobbyRequestResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRef.GetTenantLobbyRefRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRef.GetTenantLobbyRefResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantLobbyRefCreatedEventBodyModel;
import com.omgservers.schema.model.tenantLobbyRef.TenantLobbyRefModel;
import com.omgservers.schema.model.tenantLobbyRequest.TenantLobbyRequestModel;
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
public class TenantLobbyRefCreatedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;
    final LobbyModule lobbyModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_LOBBY_REF_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (TenantLobbyRefCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getVersionLobbyRef(tenantId, id)
                .flatMap(versionLobbyRef -> {
                    final var versionId = versionLobbyRef.getDeploymentId();
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

    Uni<TenantLobbyRefModel> getVersionLobbyRef(final Long tenantId, final Long id) {
        final var request = new GetTenantLobbyRefRequest(tenantId, id);
        return tenantModule.getTenantService().getVersionLobbyRef(request)
                .map(GetTenantLobbyRefResponse::getTenantLobbyRef);
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

    Uni<TenantLobbyRequestModel> findVersionLobbyRequest(final Long tenantId,
                                                         final Long versionId,
                                                         final Long lobbyId) {
        final var request = new FindTenantLobbyRequestRequest(tenantId, versionId, lobbyId);
        return tenantModule.getTenantService().findVersionLobbyRequest(request)
                .map(FindTenantLobbyRequestResponse::getTenantLobbyRequest);
    }

    Uni<Boolean> deleteVersionLobbyRequest(final Long tenantId, final Long id) {
        final var request = new DeleteTenantLobbyRequestRequest(tenantId, id);
        return tenantModule.getTenantService().deleteVersionLobbyRequest(request)
                .map(DeleteTenantLobbyRequestResponse::getDeleted);
    }
}
