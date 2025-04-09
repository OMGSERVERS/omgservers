package com.omgservers.service.server.task.impl.method.executeTenantTask;

import com.omgservers.service.server.task.dto.ExecuteTenantTaskRequest;
import com.omgservers.service.server.task.dto.ExecuteTenantTaskResponse;
import io.smallrye.mutiny.Uni;

public interface ExecuteTenantTaskMethod {
    Uni<ExecuteTenantTaskResponse> execute(ExecuteTenantTaskRequest request);
}
