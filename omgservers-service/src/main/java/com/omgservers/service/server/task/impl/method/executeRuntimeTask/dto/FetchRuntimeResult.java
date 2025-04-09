package com.omgservers.service.server.task.impl.method.executeRuntimeTask.dto;

import com.omgservers.schema.model.runtimeState.RuntimeStateDto;

import java.time.Instant;
import java.util.Map;

public record FetchRuntimeResult(Long runtimeId,
                                 Instant lastActivity,
                                 RuntimeStateDto runtimeState,
                                 Map<Long, Instant> lastActivityByClientId) {
}
