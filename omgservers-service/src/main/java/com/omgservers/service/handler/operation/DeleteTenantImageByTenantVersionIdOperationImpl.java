package com.omgservers.service.handler.operation;

import com.omgservers.schema.model.tenantImage.TenantImageModel;
import com.omgservers.schema.module.tenant.tenantImage.DeleteTenantImageRequest;
import com.omgservers.schema.module.tenant.tenantImage.DeleteTenantImageResponse;
import com.omgservers.schema.module.tenant.tenantImage.ViewTenantImageRequest;
import com.omgservers.schema.module.tenant.tenantImage.ViewTenantImageResponse;
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
class DeleteTenantImageByTenantVersionIdOperationImpl implements DeleteTenantImageByTenantVersionIdOperation {

    final TenantModule tenantModule;

    @Override
    public Uni<Void> execute(final Long tenantId, final Long tenantVersionId) {
        return viewTenantImage(tenantId, tenantVersionId)
                .flatMap(tenantImages -> Multi.createFrom().iterable(tenantImages)
                        .onItem().transformToUniAndConcatenate(tenantImage ->
                                deleteTenantImage(tenantId, tenantImage.getId())
                                        .onFailure(ServerSideClientException.class)
                                        .recoverWithItem(t -> {
                                            log.warn("Failed to delete tenant image, " +
                                                            "tenantStage={}/{}, " +
                                                            "tenantImageId={}" +
                                                            "{}:{}",
                                                    tenantId,
                                                    tenantVersionId,
                                                    tenantImage.getId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        })
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    Uni<List<TenantImageModel>> viewTenantImage(final Long tenantId, final Long tenantVersionId) {
        final var request = new ViewTenantImageRequest(tenantId, tenantVersionId);
        return tenantModule.getTenantService().viewTenantImages(request)
                .map(ViewTenantImageResponse::getTenantImages);
    }

    Uni<Boolean> deleteTenantImage(final Long tenantId, final Long id) {
        final var request = new DeleteTenantImageRequest(tenantId, id);
        return tenantModule.getTenantService().deleteTenantImage(request)
                .map(DeleteTenantImageResponse::getDeleted);
    }
}
