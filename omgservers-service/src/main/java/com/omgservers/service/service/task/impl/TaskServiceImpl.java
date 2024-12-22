package com.omgservers.service.service.task.impl;

import com.omgservers.service.service.task.TaskService;
import com.omgservers.service.service.task.dto.ExecuteBootstrapTaskRequest;
import com.omgservers.service.service.task.dto.ExecuteBootstrapTaskResponse;
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
import com.omgservers.service.service.task.impl.method.executeBootstrapTask.ExecuteBootstrapTaskMethod;
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
    final ExecuteBootstrapTaskMethod executeBootstrapTaskMethod;
    final ExecuteSchedulerTaskMethod executeSchedulerTaskMethod;
    final ExecuteRuntimeTaskMethod executeRuntimeTaskMethod;
    final ExecuteTenantTaskMethod executeTenantTaskMethod;
    final ExecuteStageTaskMethod executeStageTaskMethod;
    final ExecuteRelayTaskMethod executeRelayTaskMethod;
    final ExecutePoolTaskMethod executePoolTaskMethod;

    @Override
    public Uni<ExecuteSchedulerTaskResponse> execute(@Valid final ExecuteSchedulerTaskRequest request) {
        return executeSchedulerTaskMethod.execute(request);
    }

    @Override
    public Uni<ExecuteDispatcherTaskResponse> execute(@Valid final ExecuteDispatcherTaskRequest request) {
        return executeDispatcherTaskMethod.execute(request);
    }

    @Override
    public Uni<ExecuteTenantTaskResponse> execute(@Valid final ExecuteTenantTaskRequest request) {
        return executeTenantTaskMethod.execute(request);
    }

    @Override
    public Uni<ExecuteStageTaskResponse> execute(@Valid final ExecuteStageTaskRequest request) {
        return executeStageTaskMethod.execute(request);
    }

    @Override
    public Uni<ExecuteMatchmakerTaskResponse> execute(@Valid final ExecuteMatchmakerTaskRequest request) {
        return executeMatchmakerTaskMethod.execute(request);
    }

    @Override
    public Uni<ExecuteRuntimeTaskResponse> execute(@Valid final ExecuteRuntimeTaskRequest request) {
        return executeRuntimeTaskMethod.execute(request);
    }

    @Override
    public Uni<ExecutePoolTaskResponse> execute(@Valid final ExecutePoolTaskRequest request) {
        return executePoolTaskMethod.execute(request);
    }

    @Override
    public Uni<ExecuteRelayTaskResponse> execute(@Valid final ExecuteRelayTaskRequest request) {
        return executeRelayTaskMethod.execute(request);
    }

    @Override
    public Uni<ExecuteBuildRequestTaskResponse> execute(@Valid final ExecuteBuildRequestTaskRequest request) {
        return executeBuildRequestTaskMethod.execute(request);
    }

    @Override
    public Uni<ExecuteBootstrapTaskResponse> execute(@Valid final ExecuteBootstrapTaskRequest request) {
        return executeBootstrapTaskMethod.execute(request);
    }
}
