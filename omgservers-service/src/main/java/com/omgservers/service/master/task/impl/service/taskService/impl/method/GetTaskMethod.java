package com.omgservers.service.master.task.impl.service.taskService.impl.method;

import com.omgservers.schema.master.task.GetTaskRequest;
import com.omgservers.schema.master.task.GetTaskResponse;
import io.smallrye.mutiny.Uni;

public interface GetTaskMethod {
    Uni<GetTaskResponse> execute(GetTaskRequest request);
}
