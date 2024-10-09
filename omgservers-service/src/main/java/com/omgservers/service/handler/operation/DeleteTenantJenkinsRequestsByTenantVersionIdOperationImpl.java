package com.omgservers.service.handler.operation;

import com.omgservers.schema.model.tenantJenkinsRequest.TenantJenkinsRequestModel;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.DeleteTenantJenkinsRequestRequest;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.DeleteTenantJenkinsRequestResponse;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.ViewTenantJenkinsRequestsRequest;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.ViewTenantJenkinsRequestsResponse;
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
class DeleteTenantJenkinsRequestsByTenantVersionIdOperationImpl
        implements DeleteTenantJenkinsRequestsByTenantVersionIdOperation {

    final TenantModule tenantModule;

    @Override
    public Uni<Void> execute(final Long tenantId, final Long tenantVersionId) {
        return viewTenantDeployments(tenantId, tenantVersionId)
                .flatMap(tenantJenkinsRequests -> Multi.createFrom().iterable(tenantJenkinsRequests)
                        .onItem().transformToUniAndConcatenate(tenantJenkinsRequest ->
                                deleteTenantJenkinsRequest(tenantId, tenantJenkinsRequest.getId())
                                        .onFailure(ServerSideClientException.class)
                                        .recoverWithItem(t -> {
                                            log.warn("Failed to delete tenant jenkins request, " +
                                                            "tenantVersion={}/{}, " +
                                                            "tenantJenkinsRequestId={}" +
                                                            "{}:{}",
                                                    tenantId,
                                                    tenantVersionId,
                                                    tenantJenkinsRequest.getId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        })
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    Uni<List<TenantJenkinsRequestModel>> viewTenantDeployments(final Long tenantId, final Long tenantVersionId) {
        final var request = new ViewTenantJenkinsRequestsRequest(tenantId, tenantVersionId);
        return tenantModule.getService().viewTenantJenkinsRequests(request)
                .map(ViewTenantJenkinsRequestsResponse::getTenantJenkinsRequests);
    }

    Uni<Boolean> deleteTenantJenkinsRequest(final Long tenantId, final Long id) {
        final var request = new DeleteTenantJenkinsRequestRequest(tenantId, id);
        return tenantModule.getService().deleteTenantJenkinsRequest(request)
                .map(DeleteTenantJenkinsRequestResponse::getDeleted);
    }
}
