package com.omgservers.service.service.task;

import com.omgservers.service.service.task.dto.ExecuteBootstrapTaskRequest;
import com.omgservers.service.service.task.dto.ExecuteBootstrapTaskResponse;
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

    Uni<ExecuteSchedulerTaskResponse> execute(@Valid ExecuteSchedulerTaskRequest request);

    Uni<ExecuteTenantTaskResponse> execute(@Valid ExecuteTenantTaskRequest request);

    Uni<ExecuteStageTaskResponse> execute(@Valid ExecuteStageTaskRequest request);

    Uni<ExecuteMatchmakerTaskResponse> execute(@Valid ExecuteMatchmakerTaskRequest request);

    Uni<ExecuteRuntimeTaskResponse> execute(@Valid ExecuteRuntimeTaskRequest request);

    Uni<ExecutePoolTaskResponse> execute(@Valid ExecutePoolTaskRequest request);

    Uni<ExecuteRelayTaskResponse> execute(@Valid ExecuteRelayTaskRequest request);

    Uni<ExecuteBuildRequestTaskResponse> execute(@Valid ExecuteBuildRequestTaskRequest request);

    Uni<ExecuteBootstrapTaskResponse> execute(@Valid ExecuteBootstrapTaskRequest request);
}
