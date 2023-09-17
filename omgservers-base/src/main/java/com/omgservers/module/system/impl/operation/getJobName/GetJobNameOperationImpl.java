package com.omgservers.module.system.impl.operation.getJobName;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetJobNameOperationImpl implements GetJobNameOperation {

    @Override
    public String getJobName(final Long shardKey,
                             final Long entityId) {
        return shardKey + "/" + entityId;
    }
}
