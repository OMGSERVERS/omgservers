package com.omgservers.service.server.task.impl.method.executePoolTask.operation;

import com.omgservers.service.server.task.impl.method.executePoolTask.component.PoolScheduler;
import com.omgservers.service.server.task.impl.method.executePoolTask.dto.FetchPoolResult;

public interface CreatePoolSchedulerOperation {
    PoolScheduler execute(FetchPoolResult fetchPoolResult);
}
