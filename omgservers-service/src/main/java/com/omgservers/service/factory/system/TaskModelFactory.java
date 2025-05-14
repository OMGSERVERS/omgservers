package com.omgservers.service.factory.system;

import com.omgservers.schema.model.task.TaskModel;
import com.omgservers.schema.model.task.TaskQualifierEnum;
import com.omgservers.service.operation.server.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TaskModelFactory {

    final GenerateIdOperation generateIdOperation;

    public TaskModel create(final TaskQualifierEnum qualifier,
                            final Long shardKey,
                            final Long entityId) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, qualifier, shardKey, entityId, idempotencyKey);
    }

    public TaskModel create(final Long id,
                            final TaskQualifierEnum qualifier,
                            final Long shardKey,
                            final Long entityId) {
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, qualifier, shardKey, entityId, idempotencyKey);
    }

    public TaskModel create(final TaskQualifierEnum qualifier,
                            final Long shardKey,
                            final Long entityId,
                            final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, qualifier, shardKey, entityId, idempotencyKey);
    }

    public TaskModel create(final Long id,
                            final TaskQualifierEnum qualifier,
                            final Long shardKey,
                            final Long entityId,
                            final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var task = new TaskModel();
        task.setId(id);
        task.setIdempotencyKey(idempotencyKey);
        task.setCreated(now);
        task.setModified(now);
        task.setQualifier(qualifier);
        task.setShardKey(shardKey);
        task.setEntityId(entityId);
        task.setDeleted(false);
        return task;
    }
}
