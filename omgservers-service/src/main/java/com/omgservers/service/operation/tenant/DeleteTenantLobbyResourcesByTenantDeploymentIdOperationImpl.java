package com.omgservers.service.operation.tenant;

import com.omgservers.schema.model.tenantLobbyResource.TenantLobbyResourceModel;
import com.omgservers.schema.module.tenant.tenantLobbyResource.DeleteTenantLobbyResourceRequest;
import com.omgservers.schema.module.tenant.tenantLobbyResource.DeleteTenantLobbyResourceResponse;
import com.omgservers.schema.module.tenant.tenantLobbyResource.ViewTenantLobbyResourcesRequest;
import com.omgservers.schema.module.tenant.tenantLobbyResource.ViewTenantLobbyResourcesResponse;
import com.omgservers.service.exception.ServerSideClientException;
import com.omgservers.service.shard.tenant.TenantShard;
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
class DeleteTenantLobbyResourcesByTenantDeploymentIdOperationImpl
        implements DeleteTenantLobbyResourcesByTenantDeploymentIdOperation {

    final TenantShard tenantShard;

    @Override
    public Uni<Void> execute(final Long tenantId, final Long tenantDeploymentId) {
        return viewTenantLobbyResources(tenantId, tenantDeploymentId)
                .flatMap(tenantLobbyResources -> Multi.createFrom().iterable(tenantLobbyResources)
                        .onItem().transformToUniAndConcatenate(tenantLobbyResource ->
                                deleteTenantLobbyResource(tenantId, tenantLobbyResource.getId())
                                        .onFailure(ServerSideClientException.class)
                                        .recoverWithItem(t -> {
                                            log.warn("Failed to delete tenant lobby resource, " +
                                                            "tenantDeployment={}/{}, " +
                                                            "tenantLobbyResourceId={}" +
                                                            "{}:{}",
                                                    tenantId,
                                                    tenantDeploymentId,
                                                    tenantLobbyResource.getId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        }))
                        .collect().asList())
                .replaceWithVoid();
    }

    Uni<List<TenantLobbyResourceModel>> viewTenantLobbyResources(final Long tenantId,
                                                                 final Long tenantDeploymentId) {
        final var request = new ViewTenantLobbyResourcesRequest();
        request.setTenantId(tenantId);
        request.setTenantDeploymentId(tenantDeploymentId);
        return tenantShard.getService().execute(request)
                .map(ViewTenantLobbyResourcesResponse::getTenantLobbyResources);
    }

    Uni<Boolean> deleteTenantLobbyResource(final Long tenantId, final Long id) {
        final var request = new DeleteTenantLobbyResourceRequest(tenantId, id);
        return tenantShard.getService().execute(request)
                .map(DeleteTenantLobbyResourceResponse::getDeleted);
    }
}
