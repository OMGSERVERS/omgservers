package com.omgservers.service.module.system.impl.service.taskService;

import com.omgservers.model.dto.system.task.ExecuteMatchmakerTaskRequest;
import com.omgservers.model.dto.system.task.ExecuteMatchmakerTaskResponse;
import com.omgservers.model.dto.system.task.ExecutePoolTaskRequest;
import com.omgservers.model.dto.system.task.ExecutePoolTaskResponse;
import com.omgservers.model.dto.system.task.ExecuteRelayTaskRequest;
import com.omgservers.model.dto.system.task.ExecuteRelayTaskResponse;
import com.omgservers.model.dto.system.task.ExecuteRuntimeTaskRequest;
import com.omgservers.model.dto.system.task.ExecuteRuntimeTaskResponse;
import com.omgservers.model.dto.system.task.ExecuteSchedulerTaskRequest;
import com.omgservers.model.dto.system.task.ExecuteSchedulerTaskResponse;
import com.omgservers.model.dto.system.task.ExecuteStageTaskRequest;
import com.omgservers.model.dto.system.task.ExecuteStageTaskResponse;
import com.omgservers.model.dto.system.task.ExecuteTenantTaskRequest;
import com.omgservers.model.dto.system.task.ExecuteTenantTaskResponse;
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
