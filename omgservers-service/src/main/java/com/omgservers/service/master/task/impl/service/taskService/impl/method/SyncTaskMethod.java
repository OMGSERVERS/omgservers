package com.omgservers.service.master.task.impl.service.taskService.impl.method;

import com.omgservers.schema.master.task.SyncTaskRequest;
import com.omgservers.schema.master.task.SyncTaskResponse;
import io.smallrye.mutiny.Uni;

public interface SyncTaskMethod {
    Uni<SyncTaskResponse> execute(SyncTaskRequest request);
}
