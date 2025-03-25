package com.omgservers.service.service.task.impl.method.executePoolTask.operation;

import com.omgservers.service.service.task.impl.method.executePoolTask.dto.FetchPoolResult;
import io.smallrye.mutiny.Uni;

public interface FetchPoolOperation {
    Uni<FetchPoolResult> execute(Long poolId);
}
