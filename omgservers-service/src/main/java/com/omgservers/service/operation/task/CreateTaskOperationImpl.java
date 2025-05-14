package com.omgservers.service.operation.task;

import com.omgservers.schema.master.task.SyncTaskRequest;
import com.omgservers.schema.master.task.SyncTaskResponse;
import com.omgservers.schema.model.task.TaskQualifierEnum;
import com.omgservers.service.factory.system.TaskModelFactory;
import com.omgservers.service.master.task.TaskMaster;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateTaskOperationImpl implements CreateTaskOperation {

    final TaskMaster taskMaster;

    final TaskModelFactory taskModelFactory;

    @Override
    public Uni<Boolean> execute(final TaskQualifierEnum qualifier,
                                final Long shardKey,
                                final Long entityId,
                                final String idempotencyKey) {
        final var task = taskModelFactory.create(qualifier,
                shardKey,
                entityId,
                idempotencyKey);

        final var syncEventRequest = new SyncTaskRequest(task);
        return taskMaster.getService().executeWithIdempotency(syncEventRequest)
                .map(SyncTaskResponse::getCreated);
    }

    @Override
    public Uni<Boolean> execute(final TaskQualifierEnum qualifier,
                                final Long entityId,
                                final String idempotencyKey) {
        return execute(qualifier, entityId, entityId, idempotencyKey);
    }
}
