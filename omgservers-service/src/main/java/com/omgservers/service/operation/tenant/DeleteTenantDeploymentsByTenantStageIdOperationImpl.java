package com.omgservers.service.operation.tenant;

import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import com.omgservers.schema.module.tenant.tenantDeployment.DeleteTenantDeploymentRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.DeleteTenantDeploymentResponse;
import com.omgservers.schema.module.tenant.tenantDeployment.ViewTenantDeploymentsRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.ViewTenantDeploymentsResponse;
import com.omgservers.service.exception.ServerSideClientException;
import com.omgservers.service.module.tenant.TenantModule;
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
class DeleteTenantDeploymentsByTenantStageIdOperationImpl implements DeleteTenantDeploymentsByTenantStageIdOperation {

    final TenantModule tenantModule;

    @Override
    public Uni<Void> execute(final Long tenantId, final Long tenantStageId) {
        return viewTenantDeployments(tenantId, tenantStageId)
                .flatMap(tenantDeployments -> Multi.createFrom().iterable(tenantDeployments)
                        .onItem().transformToUniAndConcatenate(tenantDeployment ->
                                deleteTenantDeployment(tenantId, tenantDeployment.getId())
                                        .onFailure(ServerSideClientException.class)
                                        .recoverWithItem(t -> {
                                            log.warn("Failed to delete deployment, " +
                                                            "tenantStage={}/{}, " +
                                                            "tenantDeploymentId={}" +
                                                            "{}:{}",
                                                    tenantId,
                                                    tenantStageId,
                                                    tenantDeployment.getId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        })
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    Uni<List<TenantDeploymentModel>> viewTenantDeployments(final Long tenantId, final Long tenantStageId) {
        final var request = new ViewTenantDeploymentsRequest(tenantId, tenantStageId);
        return tenantModule.getService().viewTenantDeployments(request)
                .map(ViewTenantDeploymentsResponse::getTenantDeployments);
    }

    Uni<Boolean> deleteTenantDeployment(final Long tenantId, final Long id) {
        final var request = new DeleteTenantDeploymentRequest(tenantId, id);
        return tenantModule.getService().deleteTenantDeployment(request)
                .map(DeleteTenantDeploymentResponse::getDeleted);
    }
}
