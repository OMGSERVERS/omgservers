package com.omgservers.service.server.task.impl.method.executePoolTask.operation;

import com.omgservers.schema.model.poolRequest.PoolRequestModel;
import com.omgservers.schema.model.poolServer.PoolServerModel;
import com.omgservers.schema.model.poolSeverContainer.PoolContainerModel;

public interface CreatePoolContainerOperation {
    PoolContainerModel execute(PoolServerModel poolServer,
                               PoolRequestModel poolRequest);
}
