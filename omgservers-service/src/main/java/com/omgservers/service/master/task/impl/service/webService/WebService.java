package com.omgservers.service.master.task.impl.service.webService;

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

public interface WebService {

    Uni<GetTaskResponse> execute(GetTaskRequest request);

    Uni<FindTaskResponse> execute(FindTaskRequest request);

    Uni<ViewTasksResponse> execute(ViewTasksRequest request);

    Uni<SyncTaskResponse> execute(SyncTaskRequest request);

    Uni<DeleteTaskResponse> execute(DeleteTaskRequest request);
}
