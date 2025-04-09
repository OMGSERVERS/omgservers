package com.omgservers.service.server.task.impl.method.executePoolTask.dto;

import com.omgservers.schema.model.poolState.PoolStateDto;

public record FetchPoolResult(Long poolId,
                              PoolStateDto poolState) {
}
