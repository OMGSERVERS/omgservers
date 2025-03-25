package com.omgservers.service.operation.runtime;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.runtime.RuntimeQualifierEnum;
import com.omgservers.schema.model.tenantImage.TenantImageModel;
import com.omgservers.schema.model.tenantImage.TenantImageQualifierEnum;
import com.omgservers.schema.module.tenant.tenantImage.ViewTenantImagesRequest;
import com.omgservers.schema.module.tenant.tenantImage.ViewTenantImagesResponse;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SelectTenantImageForRuntimeOperationImpl implements SelectTenantImageForRuntimeOperation {

    final TenantShard tenantShard;

    @Override
    public Uni<TenantImageModel> execute(final RuntimeQualifierEnum runtimeQualifier,
                                         final Long tenantId,
                                         final Long tenantVersionId) {
        return viewTenantImages(tenantId, tenantVersionId)
                .map(tenantImages -> {
                    final var universalImageOptional = getImageByQualifier(tenantImages,
                            TenantImageQualifierEnum.UNIVERSAL);
                    return universalImageOptional
                            .orElseGet(() -> switch (runtimeQualifier) {
                                case LOBBY -> {
                                    final var lobbyImageOptional = getImageByQualifier(tenantImages,
                                            TenantImageQualifierEnum.LOBBY);
                                    if (lobbyImageOptional.isPresent()) {
                                        yield lobbyImageOptional.get();
                                    } else {
                                        throw new ServerSideConflictException(
                                                ExceptionQualifierEnum.IMAGE_NOT_FOUND,
                                                "lobby image was not found");
                                    }
                                }
                                case MATCH -> {
                                    final var matchImageOptional = getImageByQualifier(tenantImages,
                                            TenantImageQualifierEnum.MATCH);
                                    if (matchImageOptional.isPresent()) {
                                        yield matchImageOptional.get();
                                    } else {
                                        throw new ServerSideConflictException(
                                                ExceptionQualifierEnum.IMAGE_NOT_FOUND,
                                                "match image was not found");
                                    }
                                }
                            });
                });
    }

    Uni<List<TenantImageModel>> viewTenantImages(final Long tenantId, final Long tenantVersionId) {
        final var request = new ViewTenantImagesRequest(tenantId, tenantVersionId);
        return tenantShard.getService().execute(request)
                .map(ViewTenantImagesResponse::getTenantImages);
    }

    Optional<TenantImageModel> getImageByQualifier(final List<TenantImageModel> tenantImages,
                                                   final TenantImageQualifierEnum qualifier) {
        return tenantImages.stream()
                .filter(tenantImage -> tenantImage.getQualifier().equals(qualifier))
                .findFirst();
    }
}
