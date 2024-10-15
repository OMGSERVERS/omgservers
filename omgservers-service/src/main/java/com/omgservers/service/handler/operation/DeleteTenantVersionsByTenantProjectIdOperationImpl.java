package com.omgservers.service.handler.operation;

import com.omgservers.schema.model.tenantVersion.TenantVersionProjectionModel;
import com.omgservers.schema.module.tenant.tenantVersion.DeleteTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.DeleteTenantVersionResponse;
import com.omgservers.schema.module.tenant.tenantVersion.ViewTenantVersionsRequest;
import com.omgservers.schema.module.tenant.tenantVersion.ViewTenantVersionsResponse;
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
class DeleteTenantVersionsByTenantProjectIdOperationImpl implements DeleteTenantVersionsByTenantProjectIdOperation {

    final TenantModule tenantModule;

    @Override
    public Uni<Void> execute(final Long tenantId, final Long tenantProjectId) {
        return viewTenantVersionProjections(tenantId, tenantProjectId)
                .flatMap(tenantVersionProjections -> Multi.createFrom().iterable(tenantVersionProjections)
                        .onItem().transformToUniAndConcatenate(tenantVersionProjection ->
                                deleteTenantVersion(tenantId, tenantVersionProjection.getId())
                                        .onFailure(ServerSideClientException.class)
                                        .recoverWithItem(t -> {
                                            log.warn("Failed to delete version, " +
                                                            "tenantProject={}/{}, " +
                                                            "tenantVersionId={}" +
                                                            "{}:{}",
                                                    tenantId,
                                                    tenantProjectId,
                                                    tenantVersionProjection.getId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        })
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    Uni<List<TenantVersionProjectionModel>> viewTenantVersionProjections(final Long tenantId,
                                                                         final Long tenantProjectId) {
        final var request = new ViewTenantVersionsRequest(tenantId, tenantProjectId);
        return tenantModule.getService().viewTenantVersions(request)
                .map(ViewTenantVersionsResponse::getTenantVersionProjections);
    }

    Uni<Boolean> deleteTenantVersion(final Long tenantId, final Long id) {
        final var request = new DeleteTenantVersionRequest(tenantId, id);
        return tenantModule.getService().deleteTenantVersion(request)
                .map(DeleteTenantVersionResponse::getDeleted);
    }
}
