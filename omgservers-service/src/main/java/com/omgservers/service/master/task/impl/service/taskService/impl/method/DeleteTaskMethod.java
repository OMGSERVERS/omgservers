package com.omgservers.service.master.task.impl.service.taskService.impl.method;

import com.omgservers.schema.master.task.DeleteTaskResponse;
import com.omgservers.schema.master.task.DeleteTaskRequest;
import io.smallrye.mutiny.Uni;

public interface DeleteTaskMethod {
    Uni<DeleteTaskResponse> execute(DeleteTaskRequest request);
}
