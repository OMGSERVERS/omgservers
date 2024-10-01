package com.omgservers.service.handler.operation;

import com.omgservers.schema.model.tenantImageRef.TenantImageRefModel;
import com.omgservers.schema.module.tenant.tenantImageRef.DeleteTenantImageRefRequest;
import com.omgservers.schema.module.tenant.tenantImageRef.DeleteTenantImageRefResponse;
import com.omgservers.schema.module.tenant.tenantImageRef.ViewTenantImageRefsRequest;
import com.omgservers.schema.module.tenant.tenantImageRef.ViewTenantImageRefsResponse;
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
class DeleteTenantImageRefByTenantVersionIdOperationImpl implements DeleteTenantImageRefByTenantVersionIdOperation {

    final TenantModule tenantModule;

    @Override
    public Uni<Void> execute(final Long tenantId, final Long tenantVersionId) {
        return viewTenantImageRef(tenantId, tenantVersionId)
                .flatMap(tenantImageRefs -> Multi.createFrom().iterable(tenantImageRefs)
                        .onItem().transformToUniAndConcatenate(tenantImageRef ->
                                deleteTenantImageRef(tenantId, tenantImageRef.getId())
                                        .onFailure(ServerSideClientException.class)
                                        .recoverWithItem(t -> {
                                            log.warn("Failed to delete tenant image ref, " +
                                                            "tenantStage={}/{}, " +
                                                            "tenantImageRefId={}" +
                                                            "{}:{}",
                                                    tenantId,
                                                    tenantVersionId,
                                                    tenantImageRef.getId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        })
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    Uni<List<TenantImageRefModel>> viewTenantImageRef(final Long tenantId, final Long tenantVersionId) {
        final var request = new ViewTenantImageRefsRequest(tenantId, tenantVersionId);
        return tenantModule.getTenantService().viewTenantImageRefs(request)
                .map(ViewTenantImageRefsResponse::getTenantImageRefs);
    }

    Uni<Boolean> deleteTenantImageRef(final Long tenantId, final Long id) {
        final var request = new DeleteTenantImageRefRequest(tenantId, id);
        return tenantModule.getTenantService().deleteTenantImageRef(request)
                .map(DeleteTenantImageRefResponse::getDeleted);
    }
}
