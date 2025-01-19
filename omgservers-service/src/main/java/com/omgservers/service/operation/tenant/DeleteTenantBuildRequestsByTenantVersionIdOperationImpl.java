package com.omgservers.service.operation.tenant;

import com.omgservers.schema.model.tenantBuildRequest.TenantBuildRequestModel;
import com.omgservers.schema.module.tenant.tenantBuildRequest.DeleteTenantBuildRequestRequest;
import com.omgservers.schema.module.tenant.tenantBuildRequest.DeleteTenantBuildRequestResponse;
import com.omgservers.schema.module.tenant.tenantBuildRequest.ViewTenantBuildRequestsRequest;
import com.omgservers.schema.module.tenant.tenantBuildRequest.ViewTenantBuildRequestsResponse;
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
class DeleteTenantBuildRequestsByTenantVersionIdOperationImpl
        implements DeleteTenantBuildRequestsByTenantVersionIdOperation {

    final TenantModule tenantModule;

    @Override
    public Uni<Void> execute(final Long tenantId, final Long tenantVersionId) {
        return viewTenantBuildRequests(tenantId, tenantVersionId)
                .flatMap(tenantBuildRequests -> Multi.createFrom().iterable(tenantBuildRequests)
                        .onItem().transformToUniAndConcatenate(tenantBuildRequest ->
                                deleteTenantBuildRequest(tenantId, tenantBuildRequest.getId())
                                        .onFailure(ServerSideClientException.class)
                                        .recoverWithItem(t -> {
                                            log.warn("Failed to delete tenant build request, " +
                                                            "tenantVersion={}/{}, " +
                                                            "tenantBuildRequestId={}" +
                                                            "{}:{}",
                                                    tenantId,
                                                    tenantVersionId,
                                                    tenantBuildRequest.getId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        })
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    Uni<List<TenantBuildRequestModel>> viewTenantBuildRequests(final Long tenantId, final Long tenantVersionId) {
        final var request = new ViewTenantBuildRequestsRequest(tenantId, tenantVersionId);
        return tenantModule.getService().viewTenantBuildRequests(request)
                .map(ViewTenantBuildRequestsResponse::getTenantBuildRequests);
    }

    Uni<Boolean> deleteTenantBuildRequest(final Long tenantId, final Long id) {
        final var request = new DeleteTenantBuildRequestRequest(tenantId, id);
        return tenantModule.getService().deleteTenantBuildRequest(request)
                .map(DeleteTenantBuildRequestResponse::getDeleted);
    }
}
