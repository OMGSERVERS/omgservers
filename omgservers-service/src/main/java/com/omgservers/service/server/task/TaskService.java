package com.omgservers.service.server.task;

import com.omgservers.service.server.task.dto.ExecuteBootstrapTaskRequest;
import com.omgservers.service.server.task.dto.ExecuteBootstrapTaskResponse;
import com.omgservers.service.server.task.dto.ExecuteDeploymentTaskRequest;
import com.omgservers.service.server.task.dto.ExecuteDeploymentTaskResponse;
import com.omgservers.service.server.task.dto.ExecuteEventHandlerTaskRequest;
import com.omgservers.service.server.task.dto.ExecuteEventHandlerTaskResponse;
import com.omgservers.service.server.task.dto.ExecuteMatchmakerTaskRequest;
import com.omgservers.service.server.task.dto.ExecuteMatchmakerTaskResponse;
import com.omgservers.service.server.task.dto.ExecutePoolTaskRequest;
import com.omgservers.service.server.task.dto.ExecutePoolTaskResponse;
import com.omgservers.service.server.task.dto.ExecuteRuntimeTaskRequest;
import com.omgservers.service.server.task.dto.ExecuteRuntimeTaskResponse;
import com.omgservers.service.server.task.dto.ExecuteSchedulerTaskRequest;
import com.omgservers.service.server.task.dto.ExecuteSchedulerTaskResponse;
import com.omgservers.service.server.task.dto.ExecuteStageTaskRequest;
import com.omgservers.service.server.task.dto.ExecuteStageTaskResponse;
import com.omgservers.service.server.task.dto.ExecuteTenantTaskRequest;
import com.omgservers.service.server.task.dto.ExecuteTenantTaskResponse;
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

    Uni<ExecuteBootstrapTaskResponse> execute(@Valid ExecuteBootstrapTaskRequest request);

    Uni<ExecuteDeploymentTaskResponse> execute(@Valid ExecuteDeploymentTaskRequest request);
}
