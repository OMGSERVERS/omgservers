package com.omgservers.service.master.task.impl.service.taskService.impl.method;

import com.omgservers.schema.master.task.FindTaskRequest;
import com.omgservers.schema.master.task.FindTaskResponse;
import io.smallrye.mutiny.Uni;

public interface FindTaskMethod {
    Uni<FindTaskResponse> execute(FindTaskRequest request);
}
