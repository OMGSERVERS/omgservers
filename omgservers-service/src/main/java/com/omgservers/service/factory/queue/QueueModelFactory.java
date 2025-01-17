package com.omgservers.service.factory.queue;

import com.omgservers.schema.model.queue.QueueModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class QueueModelFactory {

    final GenerateIdOperation generateIdOperation;

    public QueueModel create(final Long tenantId,
                             final Long deploymentId) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();

        return create(id, tenantId, deploymentId, idempotencyKey);
    }

    public QueueModel create(final Long id,
                             final Long tenantId,
                             final Long deploymentId) {
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, tenantId, deploymentId, idempotencyKey);
    }

    public QueueModel create(final Long tenantId,
                             final Long deploymentId,
                             final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, tenantId, deploymentId, idempotencyKey);
    }

    public QueueModel create(final Long id,
                             final Long tenantId,
                             final Long deploymentId,
                             final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var queue = new QueueModel();
        queue.setId(id);
        queue.setIdempotencyKey(idempotencyKey);
        queue.setCreated(now);
        queue.setModified(now);
        queue.setTenantId(tenantId);
        queue.setDeploymentId(deploymentId);
        queue.setDeleted(Boolean.FALSE);
        return queue;
    }
}
