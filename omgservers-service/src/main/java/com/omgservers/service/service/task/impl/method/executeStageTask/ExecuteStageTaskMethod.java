package com.omgservers.service.service.task.impl.method.executeStageTask;

import com.omgservers.service.service.task.dto.ExecuteStageTaskRequest;
import com.omgservers.service.service.task.dto.ExecuteStageTaskResponse;
import io.smallrye.mutiny.Uni;

public interface ExecuteStageTaskMethod {
    Uni<ExecuteStageTaskResponse> executeStageTask(ExecuteStageTaskRequest request);
}
