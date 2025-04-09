package com.omgservers.service.server.task.impl.method.executeStageTask;

import com.omgservers.service.server.task.dto.ExecuteStageTaskRequest;
import com.omgservers.service.server.task.dto.ExecuteStageTaskResponse;
import io.smallrye.mutiny.Uni;

public interface ExecuteStageTaskMethod {
    Uni<ExecuteStageTaskResponse> execute(ExecuteStageTaskRequest request);
}
