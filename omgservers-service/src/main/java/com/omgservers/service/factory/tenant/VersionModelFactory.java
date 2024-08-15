package com.omgservers.service.factory.tenant;

import com.omgservers.schema.model.version.VersionConfigDto;
import com.omgservers.schema.model.version.VersionModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class VersionModelFactory {

    final GenerateIdOperation generateIdOperation;

    public VersionModel create(
            final Long tenantId,
            final Long stageId,
            final VersionConfigDto versionConfig,
            final String base64Archive) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id,
                tenantId,
                stageId,
                versionConfig,
                base64Archive,
                idempotencyKey);
    }

    public VersionModel create(
            final Long tenantId,
            final Long stageId,
            final VersionConfigDto versionConfig,
            final String base64Archive,
            final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id,
                tenantId,
                stageId,
                versionConfig,
                base64Archive,
                idempotencyKey);
    }

    public VersionModel create(final Long id,
                               final Long tenantId,
                               final Long stageId,
                               final VersionConfigDto versionConfig,
                               final String base64Archive,
                               final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var version = new VersionModel();
        version.setId(id);
        version.setTenantId(tenantId);
        version.setStageId(stageId);
        version.setCreated(now);
        version.setModified(now);
        version.setIdempotencyKey(idempotencyKey);
        version.setConfig(versionConfig);
        version.setBase64Archive(base64Archive);
        version.setDeleted(false);
        return version;
    }
}
