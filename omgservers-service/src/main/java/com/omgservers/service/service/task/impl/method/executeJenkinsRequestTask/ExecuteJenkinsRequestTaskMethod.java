package com.omgservers.service.service.task.impl.method.executeJenkinsRequestTask;

import com.omgservers.service.service.task.dto.ExecuteJenkinsRequestTaskRequest;
import com.omgservers.service.service.task.dto.ExecuteJenkinsRequestTaskResponse;
import io.smallrye.mutiny.Uni;

public interface ExecuteJenkinsRequestTaskMethod {
    Uni<ExecuteJenkinsRequestTaskResponse> executeJenkinsRequestTask(ExecuteJenkinsRequestTaskRequest request);
}
