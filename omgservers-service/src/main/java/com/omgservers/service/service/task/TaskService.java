package com.omgservers.service.service.task;

import com.omgservers.service.service.task.dto.*;
import com.omgservers.service.service.task.dto.ExecuteEventHandlerTaskRequest;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface TaskService {

    Uni<ExecuteSchedulerTaskResponse> execute(@Valid ExecuteSchedulerTaskRequest request);

    Uni<ExecuteTenantTaskResponse> execute(@Valid ExecuteTenantTaskRequest request);

    Uni<ExecuteStageTaskResponse> execute(@Valid ExecuteStageTaskRequest request);

    Uni<ExecuteMatchmakerTaskResponse> execute(@Valid ExecuteMatchmakerTaskRequest request);

    Uni<ExecuteRuntimeTaskResponse> execute(@Valid ExecuteRuntimeTaskRequest request);

    Uni<ExecutePoolTaskResponse> execute(@Valid ExecutePoolTaskRequest request);

    Uni<ExecuteEventHandlerTaskResponse> execute(@Valid ExecuteEventHandlerTaskRequest request);

    Uni<ExecuteBuildRequestTaskResponse> execute(@Valid ExecuteBuildRequestTaskRequest request);

    Uni<ExecuteBootstrapTaskResponse> execute(@Valid ExecuteBootstrapTaskRequest request);

    Uni<ExecuteQueueTaskResponse> execute(@Valid ExecuteQueueTaskRequest request);
}
