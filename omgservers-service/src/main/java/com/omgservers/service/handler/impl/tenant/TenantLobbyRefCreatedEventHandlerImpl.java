package com.omgservers.service.handler.impl.tenant;

import com.omgservers.schema.model.tenantLobbyRef.TenantLobbyRefModel;
import com.omgservers.schema.model.tenantLobbyResource.TenantLobbyResourceModel;
import com.omgservers.schema.module.tenant.tenantLobbyRef.GetTenantLobbyRefRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRef.GetTenantLobbyRefResponse;
import com.omgservers.schema.module.tenant.tenantLobbyResource.DeleteTenantLobbyResourceRequest;
import com.omgservers.schema.module.tenant.tenantLobbyResource.DeleteTenantLobbyResourceResponse;
import com.omgservers.schema.module.tenant.tenantLobbyResource.FindTenantLobbyResourceRequest;
import com.omgservers.schema.module.tenant.tenantLobbyResource.FindTenantLobbyResourceResponse;
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
                    return findAndDeleteTenantLobbyResource(tenantId, deploymentId, lobbyId);
                })
                .replaceWithVoid();
    }

    Uni<TenantLobbyRefModel> getTenantLobbyRef(final Long tenantId, final Long id) {
        final var request = new GetTenantLobbyRefRequest(tenantId, id);
        return tenantShard.getService().getTenantLobbyRef(request)
                .map(GetTenantLobbyRefResponse::getTenantLobbyRef);
    }

    Uni<Boolean> findAndDeleteTenantLobbyResource(final Long tenantId,
                                                  final Long tenantDeploymentId,
                                                  final Long lobbyId) {
        return findTenantLobbyResource(tenantId, tenantDeploymentId, lobbyId)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithNull()
                .onItem().ifNotNull().transformToUni(tenantLobbyResource ->
                        deleteTenantLobbyResource(tenantId, tenantLobbyResource.getId()));
    }

    Uni<TenantLobbyResourceModel> findTenantLobbyResource(final Long tenantId,
                                                          final Long tenantDeploymentId,
                                                          final Long lobbyId) {
        final var request = new FindTenantLobbyResourceRequest(tenantId, tenantDeploymentId, lobbyId);
        return tenantShard.getService().execute(request)
                .map(FindTenantLobbyResourceResponse::getTenantLobbyResource);
    }

    Uni<Boolean> deleteTenantLobbyResource(final Long tenantId, final Long id) {
        final var request = new DeleteTenantLobbyResourceRequest(tenantId, id);
        return tenantShard.getService().execute(request)
                .map(DeleteTenantLobbyResourceResponse::getDeleted);
    }
}
