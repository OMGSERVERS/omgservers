package com.omgservers.service.module.system.impl.service.taskService;

import com.omgservers.schema.service.system.task.ExecuteMatchmakerTaskRequest;
import com.omgservers.schema.service.system.task.ExecuteMatchmakerTaskResponse;
import com.omgservers.schema.service.system.task.ExecutePoolTaskRequest;
import com.omgservers.schema.service.system.task.ExecutePoolTaskResponse;
import com.omgservers.schema.service.system.task.ExecuteRelayTaskRequest;
import com.omgservers.schema.service.system.task.ExecuteRelayTaskResponse;
import com.omgservers.schema.service.system.task.ExecuteRuntimeTaskRequest;
import com.omgservers.schema.service.system.task.ExecuteRuntimeTaskResponse;
import com.omgservers.schema.service.system.task.ExecuteSchedulerTaskRequest;
import com.omgservers.schema.service.system.task.ExecuteSchedulerTaskResponse;
import com.omgservers.schema.service.system.task.ExecuteStageTaskRequest;
import com.omgservers.schema.service.system.task.ExecuteStageTaskResponse;
import com.omgservers.schema.service.system.task.ExecuteTenantTaskRequest;
import com.omgservers.schema.service.system.task.ExecuteTenantTaskResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface TaskService {

    Uni<ExecuteSchedulerTaskResponse> executeSchedulerTask(@Valid ExecuteSchedulerTaskRequest request);

    Uni<ExecuteTenantTaskResponse> executeTenantTask(@Valid ExecuteTenantTaskRequest request);

    Uni<ExecuteStageTaskResponse> executeStageTask(@Valid ExecuteStageTaskRequest request);

    Uni<ExecuteMatchmakerTaskResponse> executeMatchmakerTask(@Valid ExecuteMatchmakerTaskRequest request);

    Uni<ExecuteRuntimeTaskResponse> executeRuntimeTask(@Valid ExecuteRuntimeTaskRequest request);

    Uni<ExecutePoolTaskResponse> executePoolTask(@Valid ExecutePoolTaskRequest request);

    Uni<ExecuteRelayTaskResponse> executeRelayTask(@Valid ExecuteRelayTaskRequest request);
}
