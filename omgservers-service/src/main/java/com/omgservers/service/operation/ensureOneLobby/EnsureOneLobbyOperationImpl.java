package com.omgservers.service.operation.ensureOneLobby;

import com.omgservers.schema.model.tenantLobbyRef.TenantLobbyRefModel;
import com.omgservers.schema.model.tenantLobbyRequest.TenantLobbyRequestModel;
import com.omgservers.schema.module.tenant.tenantLobbyRef.ViewTenantLobbyRefsRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRef.ViewTenantLobbyRefsResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.SyncTenantLobbyRequestRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.SyncTenantLobbyRequestResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.ViewTenantLobbyRequestsRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.ViewTenantLobbyRequestsResponse;
import com.omgservers.service.factory.tenant.TenantLobbyRequestModelFactory;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class EnsureOneLobbyOperationImpl implements EnsureOneLobbyOperation {

    final TenantModule tenantModule;

    final TenantLobbyRequestModelFactory tenantLobbyRequestModelFactory;

    @Override
    public Uni<Void> execute(final Long tenantId,
                             final Long tenantDeploymentId) {
        return viewTenantLobbyRefs(tenantId, tenantDeploymentId)
                .flatMap(tenantLobbyRefs -> {
                    if (tenantLobbyRefs.isEmpty()) {
                        return viewTenantLobbyRequests(tenantId, tenantDeploymentId)
                                .flatMap(tenantLobbyRequests -> {
                                    if (tenantLobbyRequests.isEmpty()) {
                                        log.info("Requesting a new lobby, deployment={}/{}",
                                                tenantId, tenantDeploymentId);
                                        return createTenantLobbyRequest(tenantId, tenantDeploymentId);
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
        return tenantModule.getService().viewTenantLobbyRefs(request)
                .map(ViewTenantLobbyRefsResponse::getTenantLobbyRefs);
    }

    Uni<List<TenantLobbyRequestModel>> viewTenantLobbyRequests(final Long tenantId,
                                                               final Long tenantDeploymentId) {
        final var request = new ViewTenantLobbyRequestsRequest(tenantId, tenantDeploymentId);
        return tenantModule.getService().viewTenantLobbyRequests(request)
                .map(ViewTenantLobbyRequestsResponse::getTenantLobbyRequests);
    }

    Uni<Boolean> createTenantLobbyRequest(final Long tenantId,
                                          final Long tenantDeploymentId) {
        final var tenantLobbyRequest = tenantLobbyRequestModelFactory
                .create(tenantId, tenantDeploymentId);
        final var request = new SyncTenantLobbyRequestRequest(tenantLobbyRequest);
        return tenantModule.getService().syncTenantLobbyRequestWithIdempotency(request)
                .map(SyncTenantLobbyRequestResponse::getCreated);
    }
}
