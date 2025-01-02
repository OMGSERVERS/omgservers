package com.omgservers.dispatcher.service.task;

import com.omgservers.dispatcher.service.task.dto.ExecuteDispatcherTaskRequest;
import com.omgservers.dispatcher.service.task.dto.ExecuteDispatcherTaskResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface TaskService {

    Uni<ExecuteDispatcherTaskResponse> execute(@Valid ExecuteDispatcherTaskRequest request);
}
