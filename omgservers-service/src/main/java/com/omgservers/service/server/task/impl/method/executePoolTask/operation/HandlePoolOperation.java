package com.omgservers.service.server.task.impl.method.executePoolTask.operation;

import com.omgservers.service.server.task.impl.method.executePoolTask.dto.FetchPoolResult;
import com.omgservers.service.server.task.impl.method.executePoolTask.dto.HandlePoolResult;

public interface HandlePoolOperation {
    HandlePoolResult execute(FetchPoolResult fetchPoolResult);
}
