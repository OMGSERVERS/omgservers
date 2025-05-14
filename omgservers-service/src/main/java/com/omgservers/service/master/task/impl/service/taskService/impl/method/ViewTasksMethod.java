package com.omgservers.service.master.task.impl.service.taskService.impl.method;

import com.omgservers.schema.master.task.ViewTasksRequest;
import com.omgservers.schema.master.task.ViewTasksResponse;
import io.smallrye.mutiny.Uni;

public interface ViewTasksMethod {
    Uni<ViewTasksResponse> execute(ViewTasksRequest request);
}
