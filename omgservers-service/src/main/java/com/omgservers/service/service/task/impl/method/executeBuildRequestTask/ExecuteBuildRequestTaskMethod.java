package com.omgservers.service.service.task.impl.method.executeBuildRequestTask;

import com.omgservers.service.service.task.dto.ExecuteBuildRequestTaskRequest;
import com.omgservers.service.service.task.dto.ExecuteBuildRequestTaskResponse;
import io.smallrye.mutiny.Uni;

public interface ExecuteBuildRequestTaskMethod {
    Uni<ExecuteBuildRequestTaskResponse> execute(ExecuteBuildRequestTaskRequest request);
}
