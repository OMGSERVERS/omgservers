package com.omgservers.service.service.task.impl.method.executePoolTask.operation;

import com.omgservers.service.service.task.impl.method.executePoolTask.dto.FetchPoolResult;
import com.omgservers.service.service.task.impl.method.executePoolTask.dto.HandlePoolResult;

public interface HandlePoolRequestsOperation {
    void execute(FetchPoolResult fetchPoolResult,
                 HandlePoolResult handlePoolResult);
}
