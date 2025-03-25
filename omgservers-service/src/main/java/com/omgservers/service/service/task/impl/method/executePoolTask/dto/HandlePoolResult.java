package com.omgservers.service.service.task.impl.method.executePoolTask.dto;

import com.omgservers.schema.model.poolChangeOfState.PoolChangeOfStateDto;

public record HandlePoolResult(Long poolId,
                               PoolChangeOfStateDto poolChangeOfState) {
}
