package com.omgservers.service.module.system.impl.service.taskService.impl;

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
import com.omgservers.service.module.system.impl.service.taskService.TaskService;
import com.omgservers.service.module.system.impl.service.taskService.impl.method.executeMatchmakerTask.ExecuteMatchmakerTaskMethod;
import com.omgservers.service.module.system.impl.service.taskService.impl.method.executePoolTask.ExecutePoolTaskMethod;
import com.omgservers.service.module.system.impl.service.taskService.impl.method.executeRelayTask.ExecuteRelayTaskMethod;
import com.omgservers.service.module.system.impl.service.taskService.impl.method.executeRuntimeTask.ExecuteRuntimeTaskMethod;
import com.omgservers.service.module.system.impl.service.taskService.impl.method.executeSchedulerTask.ExecuteSchedulerTaskMethod;
import com.omgservers.service.module.system.impl.service.taskService.impl.method.executeStageTask.ExecuteStageTaskMethod;
import com.omgservers.service.module.system.impl.service.taskService.impl.method.executeTenantTask.ExecuteTenantTaskMethod;
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
