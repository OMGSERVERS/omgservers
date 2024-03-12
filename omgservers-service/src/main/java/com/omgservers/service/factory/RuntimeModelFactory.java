package com.omgservers.service.factory;

import com.omgservers.model.runtime.RuntimeConfigModel;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.model.runtime.RuntimeQualifierEnum;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RuntimeModelFactory {

    final GenerateIdOperation generateIdOperation;

    public RuntimeModel create(final Long tenantId,
                               final Long versionId,
                               final RuntimeQualifierEnum qualifier,
                               final RuntimeConfigModel config) {
        final var id = generateIdOperation.generateId();
        final var userId = generateIdOperation.generateId();
        final var idempotencyKey = UUID.randomUUID().toString();
        return create(id, tenantId, versionId, qualifier, userId, config, idempotencyKey);
    }

    public RuntimeModel create(final Long tenantId,
                               final Long versionId,
                               final RuntimeQualifierEnum qualifier,
                               final RuntimeConfigModel config,
                               final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        final var userId = generateIdOperation.generateId();
        return create(id, tenantId, versionId, qualifier, userId, config, idempotencyKey);
    }

    public RuntimeModel create(final Long id,
                               final Long tenantId,
                               final Long versionId,
                               final RuntimeQualifierEnum qualifier,
                               final Long userId,
                               final RuntimeConfigModel config,
                               final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var runtime = new RuntimeModel();
        runtime.setId(id);
        runtime.setIdempotencyKey(idempotencyKey);
        runtime.setCreated(now);
        runtime.setModified(now);
        runtime.setTenantId(tenantId);
        runtime.setVersionId(versionId);
        runtime.setQualifier(qualifier);
        runtime.setUserId(userId);
        runtime.setLastActivity(now);
        runtime.setConfig(config);
        runtime.setDeleted(Boolean.FALSE);
        return runtime;
    }
}
