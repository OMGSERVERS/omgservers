package com.omgservers.service.service.task.impl;

import com.omgservers.service.service.task.TaskService;
import com.omgservers.service.service.task.dto.*;
import com.omgservers.service.service.task.dto.ExecuteEventHandlerTaskRequest;
import com.omgservers.service.service.task.impl.method.executeBootstrapTask.ExecuteBootstrapTaskMethod;
import com.omgservers.service.service.task.impl.method.executeMatchmakerTask.ExecuteMatchmakerTaskMethod;
import com.omgservers.service.service.task.impl.method.executePoolTask.ExecutePoolTaskMethod;
import com.omgservers.service.service.task.impl.method.executeDeploymentTask.ExecuteDeploymentTaskMethod;
import com.omgservers.service.service.task.impl.method.executeEventHandlerTask.ExecuteEventHandlerTaskMethod;
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

    final ExecuteEventHandlerTaskMethod executeEventHandlerTaskMethod;
    final ExecuteDeploymentTaskMethod executeDeploymentTaskMethod;
    final ExecuteMatchmakerTaskMethod executeMatchmakerTaskMethod;
    final ExecuteBootstrapTaskMethod executeBootstrapTaskMethod;
    final ExecuteSchedulerTaskMethod executeSchedulerTaskMethod;
    final ExecuteRuntimeTaskMethod executeRuntimeTaskMethod;
    final ExecuteTenantTaskMethod executeTenantTaskMethod;
    final ExecuteStageTaskMethod executeStageTaskMethod;
    final ExecutePoolTaskMethod executePoolTaskMethod;

    @Override
    public Uni<ExecuteSchedulerTaskResponse> execute(@Valid final ExecuteSchedulerTaskRequest request) {
        return executeSchedulerTaskMethod.execute(request);
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
    public Uni<ExecuteDeploymentTaskResponse> execute(@Valid final ExecuteDeploymentTaskRequest request) {
        return executeDeploymentTaskMethod.execute(request);
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
    public Uni<ExecuteEventHandlerTaskResponse> execute(@Valid final ExecuteEventHandlerTaskRequest request) {
        return executeEventHandlerTaskMethod.execute(request);
    }

    @Override
    public Uni<ExecuteBootstrapTaskResponse> execute(@Valid final ExecuteBootstrapTaskRequest request) {
        return executeBootstrapTaskMethod.execute(request);
    }
}
