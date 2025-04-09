package com.omgservers.service.server.task.impl.method.executeSchedulerTask;

import com.omgservers.service.server.task.dto.ExecuteSchedulerTaskRequest;
import com.omgservers.service.server.task.dto.ExecuteSchedulerTaskResponse;
import io.smallrye.mutiny.Uni;

public interface ExecuteSchedulerTaskMethod {
    Uni<ExecuteSchedulerTaskResponse> execute(ExecuteSchedulerTaskRequest request);
}
