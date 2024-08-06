package com.omgservers.service.server.service.task.impl.method.executeSchedulerTask;

import com.omgservers.schema.service.system.task.ExecuteSchedulerTaskRequest;
import com.omgservers.schema.service.system.task.ExecuteSchedulerTaskResponse;
import io.smallrye.mutiny.Uni;

public interface ExecuteSchedulerTaskMethod {
    Uni<ExecuteSchedulerTaskResponse> executeSchedulerTask(ExecuteSchedulerTaskRequest request);
}
