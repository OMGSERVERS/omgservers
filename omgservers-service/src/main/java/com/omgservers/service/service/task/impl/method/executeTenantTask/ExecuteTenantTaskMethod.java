package com.omgservers.service.service.task.impl.method.executeTenantTask;

import com.omgservers.service.service.task.dto.ExecuteTenantTaskRequest;
import com.omgservers.service.service.task.dto.ExecuteTenantTaskResponse;
import io.smallrye.mutiny.Uni;

public interface ExecuteTenantTaskMethod {
    Uni<ExecuteTenantTaskResponse> execute(ExecuteTenantTaskRequest request);
}
