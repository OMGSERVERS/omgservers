package com.omgservers.service.service.task.impl.method.executeRuntimeTask.operation;

import io.smallrye.mutiny.Uni;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public interface GetClientsLastActivitiesOperation {
    Uni<Map<Long, Instant>> execute(List<Long> clientIds);
}
