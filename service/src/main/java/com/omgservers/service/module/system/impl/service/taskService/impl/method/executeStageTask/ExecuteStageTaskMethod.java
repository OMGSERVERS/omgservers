package com.omgservers.service.module.system.impl.service.taskService.impl.method.executeStageTask;

import com.omgservers.model.dto.system.task.ExecuteStageTaskRequest;
import com.omgservers.model.dto.system.task.ExecuteStageTaskResponse;
import io.smallrye.mutiny.Uni;

public interface ExecuteStageTaskMethod {
    Uni<ExecuteStageTaskResponse> executeStageTask(ExecuteStageTaskRequest request);
}
