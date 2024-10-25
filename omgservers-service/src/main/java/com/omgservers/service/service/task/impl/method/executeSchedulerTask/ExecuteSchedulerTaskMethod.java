package com.omgservers.service.service.task.impl.method.executeSchedulerTask;

import com.omgservers.service.service.task.dto.ExecuteSchedulerTaskRequest;
import com.omgservers.service.service.task.dto.ExecuteSchedulerTaskResponse;
import io.smallrye.mutiny.Uni;

public interface ExecuteSchedulerTaskMethod {
    Uni<ExecuteSchedulerTaskResponse> execute(ExecuteSchedulerTaskRequest request);
}
