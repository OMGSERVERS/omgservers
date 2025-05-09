package com.omgservers.service.factory.runtime;

import com.omgservers.schema.model.runtime.RuntimeConfigDto;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.model.runtime.RuntimeQualifierEnum;
import com.omgservers.service.operation.server.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RuntimeModelFactory {

    final GenerateIdOperation generateIdOperation;

    public RuntimeModel create(final Long deploymentId,
                               final RuntimeQualifierEnum qualifier,
                               final RuntimeConfigDto config) {
        final var id = generateIdOperation.generateId();
        final var userId = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, deploymentId, qualifier, userId, config, idempotencyKey);
    }

    public RuntimeModel create(final Long id,
                               final Long deploymentId,
                               final RuntimeQualifierEnum qualifier,
                               final RuntimeConfigDto config) {
        final var userId = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, deploymentId, qualifier, userId, config, idempotencyKey);
    }

    public RuntimeModel create(final Long deploymentId,
                               final RuntimeQualifierEnum qualifier,
                               final RuntimeConfigDto config,
                               final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        final var userId = generateIdOperation.generateId();
        return create(id, deploymentId, qualifier, userId, config, idempotencyKey);
    }

    public RuntimeModel create(final Long id,
                               final Long deploymentId,
                               final RuntimeQualifierEnum qualifier,
                               final Long userId,
                               final RuntimeConfigDto config,
                               final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var runtime = new RuntimeModel();
        runtime.setId(id);
        runtime.setIdempotencyKey(idempotencyKey);
        runtime.setCreated(now);
        runtime.setModified(now);
        runtime.setDeploymentId(deploymentId);
        runtime.setQualifier(qualifier);
        runtime.setUserId(userId);
        runtime.setConfig(config);
        runtime.setDeleted(Boolean.FALSE);
        return runtime;
    }
}
