package com.omgservers.service.service.task.impl;

import com.omgservers.service.service.task.TaskService;
import com.omgservers.service.service.task.dto.ExecuteBuildRequestTaskRequest;
import com.omgservers.service.service.task.dto.ExecuteBuildRequestTaskResponse;
import com.omgservers.service.service.task.dto.ExecuteDispatcherTaskRequest;
import com.omgservers.service.service.task.dto.ExecuteDispatcherTaskResponse;
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
import com.omgservers.service.service.task.impl.method.executeBuildRequestTask.ExecuteBuildRequestTaskMethod;
import com.omgservers.service.service.task.impl.method.executeDispatcherTask.ExecuteDispatcherTaskMethod;
import com.omgservers.service.service.task.impl.method.executeMatchmakerTask.ExecuteMatchmakerTaskMethod;
import com.omgservers.service.service.task.impl.method.executePoolTask.ExecutePoolTaskMethod;
import com.omgservers.service.service.task.impl.method.executeRelayTask.ExecuteRelayTaskMethod;
import com.omgservers.service.service.task.impl.method.executeRuntimeTask.ExecuteRuntimeTaskMethod;
import com.omgservers.service.service.task.impl.method.executeSchedulerTask.ExecuteSchedulerTaskMethod;
import com.omgservers.service.service.task.impl.method.executeStageTask.ExecuteStageTaskMethod;
import com.omgservers.service.service.task.impl.method.executeTenantTask.ExecuteTenantTaskMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class TaskServiceImpl implements TaskService {

    final ExecuteBuildRequestTaskMethod executeBuildRequestTaskMethod;
    final ExecuteMatchmakerTaskMethod executeMatchmakerTaskMethod;
    final ExecuteDispatcherTaskMethod executeDispatcherTaskMethod;
    final ExecuteSchedulerTaskMethod executeSchedulerTaskMethod;
    final ExecuteRuntimeTaskMethod executeRuntimeTaskMethod;
    final ExecuteTenantTaskMethod executeTenantTaskMethod;
    final ExecuteStageTaskMethod executeStageTaskMethod;
    final ExecuteRelayTaskMethod executeRelayTaskMethod;
    final ExecutePoolTaskMethod executePoolTaskMethod;

    @Override
    public Uni<ExecuteSchedulerTaskResponse> executeSchedulerTask(@Valid final ExecuteSchedulerTaskRequest request) {
        return executeSchedulerTaskMethod.executeSchedulerTask(request);
    }

    @Override
    public Uni<ExecuteDispatcherTaskResponse> executeDispatcherTask(@Valid final ExecuteDispatcherTaskRequest request) {
        return executeDispatcherTaskMethod.execute(request);
    }

    @Override
    public Uni<ExecuteTenantTaskResponse> executeTenantTask(@Valid final ExecuteTenantTaskRequest request) {
        return executeTenantTaskMethod.executeTenantTask(request);
    }

    @Override
    public Uni<ExecuteStageTaskResponse> executeStageTask(@Valid final ExecuteStageTaskRequest request) {
        return executeStageTaskMethod.executeStageTask(request);
    }

    @Override
    public Uni<ExecuteMatchmakerTaskResponse> executeMatchmakerTask(@Valid final ExecuteMatchmakerTaskRequest request) {
        return executeMatchmakerTaskMethod.executeMatchmakerTask(request);
    }

    @Override
    public Uni<ExecuteRuntimeTaskResponse> executeRuntimeTask(@Valid final ExecuteRuntimeTaskRequest request) {
        return executeRuntimeTaskMethod.executeRuntimeTask(request);
    }

    @Override
    public Uni<ExecutePoolTaskResponse> executePoolTask(@Valid final ExecutePoolTaskRequest request) {
        return executePoolTaskMethod.executePoolTask(request);
    }

    @Override
    public Uni<ExecuteRelayTaskResponse> executeRelayTask(@Valid final ExecuteRelayTaskRequest request) {
        return executeRelayTaskMethod.executeRelayTask(request);
    }

    @Override
    public Uni<ExecuteBuildRequestTaskResponse> executeBuildRequestTask(
            @Valid final ExecuteBuildRequestTaskRequest request) {
        return executeBuildRequestTaskMethod.execute(request);
    }
}
