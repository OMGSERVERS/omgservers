package com.omgservers.service.factory.tenant;

import com.omgservers.schema.model.tenantFilesArchive.TenantFilesArchiveModel;
import com.omgservers.service.operation.server.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantFilesArchiveModelFactory {

    final GenerateIdOperation generateIdOperation;

    public TenantFilesArchiveModel create(
            final Long tenantId,
            final Long tenantVersionId,
            final String base64Archive) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id,
                tenantId,
                tenantVersionId,
                base64Archive,
                idempotencyKey);
    }

    public TenantFilesArchiveModel create(
            final Long tenantId,
            final Long tenantVersionId,
            final String base64Archive,
            final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id,
                tenantId,
                tenantVersionId,
                base64Archive,
                idempotencyKey);
    }

    public TenantFilesArchiveModel create(final Long id,
                                          final Long tenantId,
                                          final Long tenantVersionId,
                                          final String base64Archive,
                                          final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var tenantFilesArchive = new TenantFilesArchiveModel();
        tenantFilesArchive.setId(id);
        tenantFilesArchive.setIdempotencyKey(idempotencyKey);
        tenantFilesArchive.setTenantId(tenantId);
        tenantFilesArchive.setVersionId(tenantVersionId);
        tenantFilesArchive.setCreated(now);
        tenantFilesArchive.setModified(now);
        tenantFilesArchive.setBase64Archive(base64Archive);
        tenantFilesArchive.setDeleted(false);
        return tenantFilesArchive;
    }
}
