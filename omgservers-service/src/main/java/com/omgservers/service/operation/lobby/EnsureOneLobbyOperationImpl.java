package com.omgservers.service.operation.lobby;

import com.omgservers.schema.model.tenantLobbyRef.TenantLobbyRefModel;
import com.omgservers.schema.model.tenantLobbyResource.TenantLobbyResourceModel;
import com.omgservers.schema.module.tenant.tenantLobbyRef.ViewTenantLobbyRefsRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRef.ViewTenantLobbyRefsResponse;
import com.omgservers.schema.module.tenant.tenantLobbyResource.SyncTenantLobbyResourceRequest;
import com.omgservers.schema.module.tenant.tenantLobbyResource.SyncTenantLobbyResourceResponse;
import com.omgservers.schema.module.tenant.tenantLobbyResource.ViewTenantLobbyResourcesRequest;
import com.omgservers.schema.module.tenant.tenantLobbyResource.ViewTenantLobbyResourcesResponse;
import com.omgservers.service.factory.tenant.TenantLobbyResourceModelFactory;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class EnsureOneLobbyOperationImpl implements EnsureOneLobbyOperation {

    final TenantShard tenantShard;

    final TenantLobbyResourceModelFactory tenantLobbyResourceModelFactory;

    @Override
    public Uni<Void> execute(final Long tenantId,
                             final Long tenantDeploymentId) {
        return viewTenantLobbyRefs(tenantId, tenantDeploymentId)
                .flatMap(tenantLobbyRefs -> {
                    if (tenantLobbyRefs.isEmpty()) {
                        return viewTenantLobbyResources(tenantId, tenantDeploymentId)
                                .flatMap(tenantLobbyResources -> {
                                    if (tenantLobbyResources.isEmpty()) {
                                        log.info("Requesting a new lobby, deployment={}/{}",
                                                tenantId, tenantDeploymentId);
                                        return createTenantLobbyResource(tenantId, tenantDeploymentId);
                                    } else {
                                        return Uni.createFrom().voidItem();
                                    }
                                });
                    } else {
                        return Uni.createFrom().voidItem();
                    }
                })
                .replaceWithVoid();
    }

    Uni<List<TenantLobbyRefModel>> viewTenantLobbyRefs(final Long tenantId, final Long deploymentId) {
        final var request = new ViewTenantLobbyRefsRequest(tenantId, deploymentId);
        return tenantShard.getService().viewTenantLobbyRefs(request)
                .map(ViewTenantLobbyRefsResponse::getTenantLobbyRefs);
    }

    Uni<List<TenantLobbyResourceModel>> viewTenantLobbyResources(final Long tenantId,
                                                                 final Long tenantDeploymentId) {
        final var request = new ViewTenantLobbyResourcesRequest(tenantId, tenantDeploymentId);
        return tenantShard.getService().execute(request)
                .map(ViewTenantLobbyResourcesResponse::getTenantLobbyResources);
    }

    Uni<Boolean> createTenantLobbyResource(final Long tenantId,
                                           final Long tenantDeploymentId) {
        final var tenantLobbyResource = tenantLobbyResourceModelFactory
                .create(tenantId, tenantDeploymentId);
        final var request = new SyncTenantLobbyResourceRequest(tenantLobbyResource);
        return tenantShard.getService().executeWithIdempotency(request)
                .map(SyncTenantLobbyResourceResponse::getCreated);
    }
}
