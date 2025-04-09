package com.omgservers.service.server.task.impl.method.executeRuntimeTask.operation;

import com.omgservers.service.server.task.impl.method.executeRuntimeTask.dto.FetchRuntimeResult;
import io.smallrye.mutiny.Uni;

public interface FetchRuntimeOperation {
    Uni<FetchRuntimeResult> execute(Long runtimeId);
}
