package com.omgservers.service.factory.tenant;

import com.omgservers.schema.model.tenantImageRef.TenantImageRefModel;
import com.omgservers.schema.model.tenantImageRef.TenantImageRefQualifierEnum;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantImageRefModelFactory {

    final GenerateIdOperation generateIdOperation;

    public TenantImageRefModel create(final Long tenantId,
                                      final Long versionId,
                                      final TenantImageRefQualifierEnum qualifier,
                                      final String imageId) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, tenantId, versionId, qualifier, imageId, idempotencyKey);
    }

    public TenantImageRefModel create(final Long tenantId,
                                      final Long versionId,
                                      final TenantImageRefQualifierEnum qualifier,
                                      final String imageId,
                                      final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, tenantId, versionId, qualifier, imageId, idempotencyKey);
    }

    public TenantImageRefModel create(final Long id,
                                      final Long tenantId,
                                      final Long versionId,
                                      final TenantImageRefQualifierEnum qualifier,
                                      final String imageId,
                                      final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var versionImageRefModel = new TenantImageRefModel();
        versionImageRefModel.setId(id);
        versionImageRefModel.setIdempotencyKey(idempotencyKey);
        versionImageRefModel.setTenantId(tenantId);
        versionImageRefModel.setVersionId(versionId);
        versionImageRefModel.setCreated(now);
        versionImageRefModel.setModified(now);
        versionImageRefModel.setQualifier(qualifier);
        versionImageRefModel.setImageId(imageId);
        versionImageRefModel.setDeleted(false);
        return versionImageRefModel;
    }
}
