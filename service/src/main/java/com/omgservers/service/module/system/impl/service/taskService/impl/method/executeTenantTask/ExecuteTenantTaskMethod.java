package com.omgservers.service.module.system.impl.service.taskService.impl.method.executeTenantTask;

import com.omgservers.schema.service.system.task.ExecuteTenantTaskRequest;
import com.omgservers.schema.service.system.task.ExecuteTenantTaskResponse;
import io.smallrye.mutiny.Uni;

public interface ExecuteTenantTaskMethod {
    Uni<ExecuteTenantTaskResponse> executeTenantTask(ExecuteTenantTaskRequest request);
}
