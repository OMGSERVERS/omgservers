package com.omgservers.service.operation.tenant;

import com.omgservers.schema.model.tenantMatchmakerRequest.TenantMatchmakerRequestModel;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.DeleteTenantMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.DeleteTenantMatchmakerRequestResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.ViewTenantMatchmakerRequestsRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.ViewTenantMatchmakerRequestsResponse;
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
class DeleteTenantMatchmakerRequestsByTenantDeploymentIdOperationImpl
        implements DeleteTenantMatchmakerRequestsByTenantDeploymentIdOperation {

    final TenantModule tenantModule;

    @Override
    public Uni<Void> execute(final Long tenantId, final Long tenantDeploymentId) {
        return viewTenantMatchmakerRequests(tenantId, tenantDeploymentId)
                .flatMap(tenantMatchmakerRequests -> Multi.createFrom().iterable(tenantMatchmakerRequests)
                        .onItem().transformToUniAndConcatenate(tenantMatchmakerRequest ->
                                deleteTenantMatchmakerRequest(tenantId, tenantMatchmakerRequest.getId())
                                        .onFailure(ServerSideClientException.class)
                                        .recoverWithItem(t -> {
                                            log.warn("Failed to delete tenant matchmaker request, " +
                                                            "tenantDeployment={}/{}, " +
                                                            "tenantMatchmakerRequestId={}" +
                                                            "{}:{}",
                                                    tenantId,
                                                    tenantDeploymentId,
                                                    tenantMatchmakerRequest.getId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        }))
                        .collect().asList())
                .replaceWithVoid();
    }

    Uni<List<TenantMatchmakerRequestModel>> viewTenantMatchmakerRequests(final Long tenantId,
                                                                         final Long tenantDeploymentId) {
        final var request = new ViewTenantMatchmakerRequestsRequest(tenantId, tenantDeploymentId);
        return tenantModule.getService().viewTenantMatchmakerRequests(request)
                .map(ViewTenantMatchmakerRequestsResponse::getTenantMatchmakerRequests);
    }

    Uni<Boolean> deleteTenantMatchmakerRequest(final Long tenantId, final Long id) {
        final var request = new DeleteTenantMatchmakerRequestRequest(tenantId, id);
        return tenantModule.getService().deleteTenantMatchmakerRequest(request)
                .map(DeleteTenantMatchmakerRequestResponse::getDeleted);
    }
}
