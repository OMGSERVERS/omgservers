package com.omgservers.service.factory.tenant;

import com.omgservers.schema.model.tenantImage.TenantImageConfigDto;
import com.omgservers.schema.model.tenantImage.TenantImageModel;
import com.omgservers.schema.model.tenantImage.TenantImageQualifierEnum;
import com.omgservers.service.operation.server.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantImageModelFactory {

    final GenerateIdOperation generateIdOperation;

    public TenantImageModel create(final Long tenantId,
                                   final Long tenantVersionId,
                                   final TenantImageQualifierEnum qualifier,
                                   final String imageId,
                                   final TenantImageConfigDto tenantImageConfig) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, tenantId, tenantVersionId, qualifier, imageId, tenantImageConfig, idempotencyKey);
    }

    public TenantImageModel create(final Long tenantId,
                                   final Long tenantVersionId,
                                   final TenantImageQualifierEnum qualifier,
                                   final String imageId,
                                   final TenantImageConfigDto tenantImageConfig,
                                   final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, tenantId, tenantVersionId, qualifier, imageId, tenantImageConfig, idempotencyKey);
    }

    public TenantImageModel create(final Long id,
                                   final Long tenantId,
                                   final Long tenantVersionId,
                                   final TenantImageQualifierEnum qualifier,
                                   final String imageId,
                                   final TenantImageConfigDto tenantImageConfig,
                                   final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var tenantImageModel = new TenantImageModel();
        tenantImageModel.setId(id);
        tenantImageModel.setIdempotencyKey(idempotencyKey);
        tenantImageModel.setTenantId(tenantId);
        tenantImageModel.setVersionId(tenantVersionId);
        tenantImageModel.setCreated(now);
        tenantImageModel.setModified(now);
        tenantImageModel.setQualifier(qualifier);
        tenantImageModel.setImageId(imageId);
        tenantImageModel.setConfig(tenantImageConfig);
        tenantImageModel.setDeleted(false);
        return tenantImageModel;
    }
}
