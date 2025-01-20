package com.omgservers.service.operation.tenant;

import com.omgservers.schema.model.tenantLobbyRequest.TenantLobbyRequestModel;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.DeleteTenantLobbyRequestRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.DeleteTenantLobbyRequestResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.ViewTenantLobbyRequestsRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.ViewTenantLobbyRequestsResponse;
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
class DeleteTenantLobbyRequestsByTenantDeploymentIdOperationImpl
        implements DeleteTenantLobbyRequestsByTenantDeploymentIdOperation {

    final TenantShard tenantShard;

    @Override
    public Uni<Void> execute(final Long tenantId, final Long tenantDeploymentId) {
        return viewTenantLobbyRequests(tenantId, tenantDeploymentId)
                .flatMap(tenantLobbyRequests -> Multi.createFrom().iterable(tenantLobbyRequests)
                        .onItem().transformToUniAndConcatenate(tenantLobbyRequest ->
                                deleteTenantLobbyRequest(tenantId, tenantLobbyRequest.getId())
                                        .onFailure(ServerSideClientException.class)
                                        .recoverWithItem(t -> {
                                            log.warn("Failed to delete tenant lobby request, " +
                                                            "tenantDeployment={}/{}, " +
                                                            "tenantLobbyRequestId={}" +
                                                            "{}:{}",
                                                    tenantId,
                                                    tenantDeploymentId,
                                                    tenantLobbyRequest.getId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        }))
                        .collect().asList())
                .replaceWithVoid();
    }

    Uni<List<TenantLobbyRequestModel>> viewTenantLobbyRequests(final Long tenantId,
                                                               final Long tenantDeploymentId) {
        final var request = new ViewTenantLobbyRequestsRequest(tenantId, tenantDeploymentId);
        return tenantShard.getService().viewTenantLobbyRequests(request)
                .map(ViewTenantLobbyRequestsResponse::getTenantLobbyRequests);
    }

    Uni<Boolean> deleteTenantLobbyRequest(final Long tenantId, final Long id) {
        final var request = new DeleteTenantLobbyRequestRequest(tenantId, id);
        return tenantShard.getService().deleteTenantLobbyRequest(request)
                .map(DeleteTenantLobbyRequestResponse::getDeleted);
    }
}
