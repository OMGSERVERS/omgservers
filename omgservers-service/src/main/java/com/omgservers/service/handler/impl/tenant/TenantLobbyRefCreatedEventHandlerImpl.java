package com.omgservers.service.handler.impl.tenant;

import com.omgservers.schema.model.tenantLobbyRef.TenantLobbyRefModel;
import com.omgservers.schema.model.tenantLobbyRequest.TenantLobbyRequestModel;
import com.omgservers.schema.module.tenant.tenantLobbyRef.GetTenantLobbyRefRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRef.GetTenantLobbyRefResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.DeleteTenantLobbyRequestRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.DeleteTenantLobbyRequestResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.FindTenantLobbyRequestRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.FindTenantLobbyRequestResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantLobbyRefCreatedEventBodyModel;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.shard.lobby.LobbyShard;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TenantLobbyRefCreatedEventHandlerImpl implements EventHandler {

    final TenantShard tenantShard;
    final LobbyShard lobbyShard;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_LOBBY_REF_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (TenantLobbyRefCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getTenantLobbyRef(tenantId, id)
                .flatMap(tenantLobbyRef -> {
                    final var deploymentId = tenantLobbyRef.getDeploymentId();
                    final var lobbyId = tenantLobbyRef.getLobbyId();
                    log.debug("Created, {}", tenantLobbyRef);

                    // TODO: update lobby state status
                    return findAndDeleteTenantLobbyRequest(tenantId, deploymentId, lobbyId);
                })
                .replaceWithVoid();
    }

    Uni<TenantLobbyRefModel> getTenantLobbyRef(final Long tenantId, final Long id) {
        final var request = new GetTenantLobbyRefRequest(tenantId, id);
        return tenantShard.getService().getTenantLobbyRef(request)
                .map(GetTenantLobbyRefResponse::getTenantLobbyRef);
    }

    Uni<Boolean> findAndDeleteTenantLobbyRequest(final Long tenantId,
                                                 final Long tenantDeploymentId,
                                                 final Long lobbyId) {
        return findTenantLobbyRequest(tenantId, tenantDeploymentId, lobbyId)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithNull()
                .onItem().ifNotNull().transformToUni(tenantLobbyRequest ->
                        deleteTenantLobbyRequest(tenantId, tenantLobbyRequest.getId()));
    }

    Uni<TenantLobbyRequestModel> findTenantLobbyRequest(final Long tenantId,
                                                        final Long tenantDeploymentId,
                                                        final Long lobbyId) {
        final var request = new FindTenantLobbyRequestRequest(tenantId, tenantDeploymentId, lobbyId);
        return tenantShard.getService().findTenantLobbyRequest(request)
                .map(FindTenantLobbyRequestResponse::getTenantLobbyRequest);
    }

    Uni<Boolean> deleteTenantLobbyRequest(final Long tenantId, final Long id) {
        final var request = new DeleteTenantLobbyRequestRequest(tenantId, id);
        return tenantShard.getService().deleteTenantLobbyRequest(request)
                .map(DeleteTenantLobbyRequestResponse::getDeleted);
    }
}
