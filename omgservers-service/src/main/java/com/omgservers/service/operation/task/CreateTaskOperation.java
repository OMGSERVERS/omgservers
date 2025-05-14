package com.omgservers.service.operation.task;

import com.omgservers.schema.model.task.TaskQualifierEnum;
import io.smallrye.mutiny.Uni;

public interface CreateTaskOperation {
    Uni<Boolean> execute(TaskQualifierEnum qualifier,
                         Long shardKey,
                         Long entityId,
                         String idempotencyKey);

    Uni<Boolean> execute(TaskQualifierEnum qualifier,
                         Long entityId,
                         String idempotencyKey);
}
