package com.omgservers.service.master.task.impl.service.taskService.impl;

import com.omgservers.schema.master.task.DeleteTaskRequest;
import com.omgservers.schema.master.task.DeleteTaskResponse;
import com.omgservers.schema.master.task.FindTaskRequest;
import com.omgservers.schema.master.task.FindTaskResponse;
import com.omgservers.schema.master.task.GetTaskRequest;
import com.omgservers.schema.master.task.GetTaskResponse;
import com.omgservers.schema.master.task.SyncTaskRequest;
import com.omgservers.schema.master.task.SyncTaskResponse;
import com.omgservers.schema.master.task.ViewTasksRequest;
import com.omgservers.schema.master.task.ViewTasksResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.master.task.impl.operation.GetTaskMasterClientOperation;
import com.omgservers.service.master.task.impl.service.taskService.TaskService;
import com.omgservers.service.master.task.impl.service.taskService.impl.method.DeleteTaskMethod;
import com.omgservers.service.master.task.impl.service.taskService.impl.method.FindTaskMethod;
import com.omgservers.service.master.task.impl.service.taskService.impl.method.GetTaskMethod;
import com.omgservers.service.master.task.impl.service.taskService.impl.method.SyncTaskMethod;
import com.omgservers.service.master.task.impl.service.taskService.impl.method.ViewTasksMethod;
import com.omgservers.service.master.task.impl.service.webService.impl.api.TaskApi;
import com.omgservers.service.operation.server.HandleMasterRequestOperation;
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

    final DeleteTaskMethod deleteTaskMethod;
    final ViewTasksMethod viewTasksMethod;
    final SyncTaskMethod syncTaskMethod;
    final FindTaskMethod findTaskMethod;
    final GetTaskMethod getTaskMethod;

    final HandleMasterRequestOperation handleMasterRequestOperation;
    final GetTaskMasterClientOperation getTaskMasterClientOperation;

    @Override
    public Uni<GetTaskResponse> execute(@Valid final GetTaskRequest request) {
        return handleMasterRequestOperation.execute(log, request,
                getTaskMasterClientOperation::execute,
                TaskApi::execute,
                getTaskMethod::execute);
    }

    @Override
    public Uni<FindTaskResponse> execute(@Valid final FindTaskRequest request) {
        return handleMasterRequestOperation.execute(log, request,
                getTaskMasterClientOperation::execute,
                TaskApi::execute,
                findTaskMethod::execute);
    }

    @Override
    public Uni<ViewTasksResponse> execute(@Valid final ViewTasksRequest request) {
        return handleMasterRequestOperation.execute(log, request,
                getTaskMasterClientOperation::execute,
                TaskApi::execute,
                viewTasksMethod::execute);
    }

    @Override
    public Uni<SyncTaskResponse> execute(@Valid final SyncTaskRequest request) {
        return handleMasterRequestOperation.execute(log, request,
                getTaskMasterClientOperation::execute,
                TaskApi::execute,
                syncTaskMethod::execute);
    }

    @Override
    public Uni<SyncTaskResponse> executeWithIdempotency(@Valid SyncTaskRequest request) {
        return execute(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}", request.getTask(), t.getMessage());
                            return Uni.createFrom().item(new SyncTaskResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteTaskResponse> execute(@Valid final DeleteTaskRequest request) {
        return handleMasterRequestOperation.execute(log, request,
                getTaskMasterClientOperation::execute,
                TaskApi::execute,
                deleteTaskMethod::execute);
    }
}
