package com.omgservers.service.server.service.task.impl.method.executeStageTask;

import com.omgservers.schema.service.system.task.ExecuteStageTaskRequest;
import com.omgservers.schema.service.system.task.ExecuteStageTaskResponse;
import io.smallrye.mutiny.Uni;

public interface ExecuteStageTaskMethod {
    Uni<ExecuteStageTaskResponse> executeStageTask(ExecuteStageTaskRequest request);
}
