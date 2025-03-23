package com.omgservers.service.operation.tenant;

import com.omgservers.schema.model.tenantMatchmakerResource.TenantMatchmakerResourceModel;
import com.omgservers.schema.module.tenant.tenantMatchmakerResource.DeleteTenantMatchmakerResourceRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerResource.DeleteTenantMatchmakerResourceResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerResource.ViewTenantMatchmakerResourcesRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerResource.ViewTenantMatchmakerResourcesResponse;
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
class DeleteTenantMatchmakerResourcesByTenantDeploymentIdOperationImpl
        implements DeleteTenantMatchmakerResourcesByTenantDeploymentIdOperation {

    final TenantShard tenantShard;

    @Override
    public Uni<Void> execute(final Long tenantId, final Long tenantDeploymentId) {
        return viewTenantMatchmakerResources(tenantId, tenantDeploymentId)
                .flatMap(tenantMatchmakerResources -> Multi.createFrom().iterable(tenantMatchmakerResources)
                        .onItem().transformToUniAndConcatenate(tenantMatchmakerResource ->
                                deleteTenantMatchmakerResource(tenantId, tenantMatchmakerResource.getId())
                                        .onFailure(ServerSideClientException.class)
                                        .recoverWithItem(t -> {
                                            log.warn("Failed to delete tenant matchmaker resource, " +
                                                            "tenantDeployment={}/{}, " +
                                                            "tenantMatchmakerResourceId={}" +
                                                            "{}:{}",
                                                    tenantId,
                                                    tenantDeploymentId,
                                                    tenantMatchmakerResource.getId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        }))
                        .collect().asList())
                .replaceWithVoid();
    }

    Uni<List<TenantMatchmakerResourceModel>> viewTenantMatchmakerResources(final Long tenantId,
                                                                           final Long tenantDeploymentId) {
        final var request = new ViewTenantMatchmakerResourcesRequest(tenantId, tenantDeploymentId);
        return tenantShard.getService().execute(request)
                .map(ViewTenantMatchmakerResourcesResponse::getTenantMatchmakerResources);
    }

    Uni<Boolean> deleteTenantMatchmakerResource(final Long tenantId, final Long id) {
        final var request = new DeleteTenantMatchmakerResourceRequest(tenantId, id);
        return tenantShard.getService().execute(request)
                .map(DeleteTenantMatchmakerResourceResponse::getDeleted);
    }
}
