package com.omgservers.service.service.task.impl.method.executePoolTask.operation;

import com.omgservers.schema.model.poolChangeOfState.PoolChangeOfStateDto;
import com.omgservers.schema.model.poolState.PoolStateDto;

public interface HandlePoolRequestsOperation {
    void execute(PoolStateDto poolState,
                 PoolChangeOfStateDto poolChangeOfState);
}
