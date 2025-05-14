package com.omgservers.service.master.task.impl.service.webService.impl;

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
import com.omgservers.service.master.task.impl.service.taskService.TaskService;
import com.omgservers.service.master.task.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class WebServiceImpl implements WebService {

    final TaskService taskService;

    @Override
    public Uni<GetTaskResponse> execute(final GetTaskRequest request) {
        return taskService.execute(request);
    }

    @Override
    public Uni<FindTaskResponse> execute(final FindTaskRequest request) {
        return taskService.execute(request);
    }

    @Override
    public Uni<ViewTasksResponse> execute(final ViewTasksRequest request) {
        return taskService.execute(request);
    }

    @Override
    public Uni<SyncTaskResponse> execute(final SyncTaskRequest request) {
        return taskService.execute(request);
    }

    @Override
    public Uni<DeleteTaskResponse> execute(final DeleteTaskRequest request) {
        return taskService.execute(request);
    }
}
