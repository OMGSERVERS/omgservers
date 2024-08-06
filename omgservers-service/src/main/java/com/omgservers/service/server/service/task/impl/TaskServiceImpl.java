package com.omgservers.service.server.service.task.impl;

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
import com.omgservers.service.server.service.task.impl.method.executePoolTask.ExecutePoolTaskMethod;
import com.omgservers.service.server.service.task.impl.method.executeRelayTask.ExecuteRelayTaskMethod;
import com.omgservers.service.server.service.task.impl.method.executeRuntimeTask.ExecuteRuntimeTaskMethod;
import com.omgservers.service.server.service.task.impl.method.executeSchedulerTask.ExecuteSchedulerTaskMethod;
import com.omgservers.service.server.service.task.impl.method.executeStageTask.ExecuteStageTaskMethod;
import com.omgservers.service.server.service.task.impl.method.executeTenantTask.ExecuteTenantTaskMethod;
import com.omgservers.service.server.service.task.TaskService;
import com.omgservers.service.server.service.task.impl.method.executeMatchmakerTask.ExecuteMatchmakerTaskMethod;
import com.omgservers.service.server.service.task.impl.method.executePoolTask.ExecutePoolTaskMethod;
import com.omgservers.service.server.service.task.impl.method.executeRelayTask.ExecuteRelayTaskMethod;
import com.omgservers.service.server.service.task.impl.method.executeRuntimeTask.ExecuteRuntimeTaskMethod;
import com.omgservers.service.server.service.task.impl.method.executeSchedulerTask.ExecuteSchedulerTaskMethod;
import com.omgservers.service.server.service.task.impl.method.executeStageTask.ExecuteStageTaskMethod;
import com.omgservers.service.server.service.task.impl.method.executeTenantTask.ExecuteTenantTaskMethod;
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

    final ExecuteMatchmakerTaskMethod executeMatchmakerTaskMethod;
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
}
