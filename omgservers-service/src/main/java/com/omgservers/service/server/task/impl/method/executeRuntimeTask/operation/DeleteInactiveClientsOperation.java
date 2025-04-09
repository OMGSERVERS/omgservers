package com.omgservers.service.server.task.impl.method.executeRuntimeTask.operation;

import io.smallrye.mutiny.Uni;

import java.util.List;

public interface DeleteInactiveClientsOperation {
    Uni<Void> execute(List<Long> inactiveClients);
}
