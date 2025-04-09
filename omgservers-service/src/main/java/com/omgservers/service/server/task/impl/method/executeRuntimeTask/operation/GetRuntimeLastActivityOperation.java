package com.omgservers.service.server.task.impl.method.executeRuntimeTask.operation;

import io.smallrye.mutiny.Uni;

import java.time.Instant;

public interface GetRuntimeLastActivityOperation {
    Uni<Instant> execute(Long runtimeId);
}
