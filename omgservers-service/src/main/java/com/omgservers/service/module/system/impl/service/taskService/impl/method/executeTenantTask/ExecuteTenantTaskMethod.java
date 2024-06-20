package com.omgservers.service.module.system.impl.service.taskService.impl.method.executeTenantTask;

import com.omgservers.model.dto.system.task.ExecuteTenantTaskRequest;
import com.omgservers.model.dto.system.task.ExecuteTenantTaskResponse;
import io.smallrye.mutiny.Uni;

public interface ExecuteTenantTaskMethod {
    Uni<ExecuteTenantTaskResponse> executeTenantTask(ExecuteTenantTaskRequest request);
}
