package com.omgservers.service.service.task.impl.method.executePoolTask.operation;

import com.omgservers.service.service.task.impl.method.executePoolTask.component.PoolScheduler;
import com.omgservers.service.service.task.impl.method.executePoolTask.dto.FetchPoolResult;

public interface CreatePoolSchedulerOperation {
    PoolScheduler execute(FetchPoolResult fetchPoolResult);
}
