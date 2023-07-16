package com.omgservers.application.module.internalModule.impl.operation.getJobNameOperation;

import java.util.UUID;

public interface GetJobNameOperation {
    String getJobName(UUID shardKey, UUID entity);
}
