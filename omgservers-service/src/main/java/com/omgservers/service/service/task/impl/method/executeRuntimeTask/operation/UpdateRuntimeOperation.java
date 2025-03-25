package com.omgservers.service.service.task.impl.method.executeRuntimeTask.operation;

import com.omgservers.service.service.task.impl.method.executeRuntimeTask.dto.HandleRuntimeResult;
import io.smallrye.mutiny.Uni;

public interface UpdateRuntimeOperation {
    Uni<Void> execute(HandleRuntimeResult handleRuntimeResult);
}
