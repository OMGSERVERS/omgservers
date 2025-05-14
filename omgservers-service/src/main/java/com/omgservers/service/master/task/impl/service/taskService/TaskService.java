package com.omgservers.service.master.task.impl.service.taskService;

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
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface TaskService {

    Uni<GetTaskResponse> execute(@Valid GetTaskRequest request);

    Uni<FindTaskResponse> execute(@Valid FindTaskRequest request);

    Uni<ViewTasksResponse> execute(@Valid ViewTasksRequest request);

    Uni<SyncTaskResponse> execute(@Valid SyncTaskRequest request);

    Uni<SyncTaskResponse> executeWithIdempotency(@Valid SyncTaskRequest request);

    Uni<DeleteTaskResponse> execute(@Valid DeleteTaskRequest request);
}
