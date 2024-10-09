package com.omgservers.service.handler.operation;

import com.omgservers.schema.model.tenantImage.TenantImageModel;
import com.omgservers.schema.module.tenant.tenantImage.DeleteTenantImageRequest;
import com.omgservers.schema.module.tenant.tenantImage.DeleteTenantImageResponse;
import com.omgservers.schema.module.tenant.tenantImage.ViewTenantImagesRequest;
import com.omgservers.schema.module.tenant.tenantImage.ViewTenantImagesResponse;
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
class DeleteTenantImagesByTenantVersionIdOperationImpl implements DeleteTenantImagesByTenantVersionIdOperation {

    final TenantModule tenantModule;

    @Override
    public Uni<Void> execute(final Long tenantId, final Long tenantVersionId) {
        return viewTenantImages(tenantId, tenantVersionId)
                .flatMap(tenantImages -> Multi.createFrom().iterable(tenantImages)
                        .onItem().transformToUniAndConcatenate(tenantImage ->
                                deleteTenantImage(tenantId, tenantImage.getId())
                                        .onFailure(ServerSideClientException.class)
                                        .recoverWithItem(t -> {
                                            log.warn("Failed to delete tenant image, " +
                                                            "tenantVersion={}/{}, " +
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

    Uni<List<TenantImageModel>> viewTenantImages(final Long tenantId, final Long tenantVersionId) {
        final var request = new ViewTenantImagesRequest(tenantId, tenantVersionId);
        return tenantModule.getService().viewTenantImages(request)
                .map(ViewTenantImagesResponse::getTenantImages);
    }

    Uni<Boolean> deleteTenantImage(final Long tenantId, final Long id) {
        final var request = new DeleteTenantImageRequest(tenantId, id);
        return tenantModule.getService().deleteTenantImage(request)
                .map(DeleteTenantImageResponse::getDeleted);
    }
}
