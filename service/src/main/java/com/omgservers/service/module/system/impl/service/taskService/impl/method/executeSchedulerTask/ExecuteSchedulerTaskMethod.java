package com.omgservers.service.module.system.impl.service.taskService.impl.method.executeSchedulerTask;

import com.omgservers.schema.service.system.task.ExecuteSchedulerTaskRequest;
import com.omgservers.schema.service.system.task.ExecuteSchedulerTaskResponse;
import io.smallrye.mutiny.Uni;

public interface ExecuteSchedulerTaskMethod {
    Uni<ExecuteSchedulerTaskResponse> executeSchedulerTask(ExecuteSchedulerTaskRequest request);
}
