package com.omgservers.service.service.task;

import com.omgservers.service.service.task.dto.ExecuteBuildRequestTaskRequest;
import com.omgservers.service.service.task.dto.ExecuteBuildRequestTaskResponse;
import com.omgservers.service.service.task.dto.ExecuteMatchmakerTaskRequest;
import com.omgservers.service.service.task.dto.ExecuteMatchmakerTaskResponse;
import com.omgservers.service.service.task.dto.ExecutePoolTaskRequest;
import com.omgservers.service.service.task.dto.ExecutePoolTaskResponse;
import com.omgservers.service.service.task.dto.ExecuteRelayTaskRequest;
import com.omgservers.service.service.task.dto.ExecuteRelayTaskResponse;
import com.omgservers.service.service.task.dto.ExecuteRuntimeTaskRequest;
import com.omgservers.service.service.task.dto.ExecuteRuntimeTaskResponse;
import com.omgservers.service.service.task.dto.ExecuteSchedulerTaskRequest;
import com.omgservers.service.service.task.dto.ExecuteSchedulerTaskResponse;
import com.omgservers.service.service.task.dto.ExecuteStageTaskRequest;
import com.omgservers.service.service.task.dto.ExecuteStageTaskResponse;
import com.omgservers.service.service.task.dto.ExecuteTenantTaskRequest;
import com.omgservers.service.service.task.dto.ExecuteTenantTaskResponse;
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

    Uni<ExecuteBuildRequestTaskResponse> executeBuildRequestTask(@Valid ExecuteBuildRequestTaskRequest request);
}
