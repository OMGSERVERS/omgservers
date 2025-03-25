package com.omgservers.service.service.task.impl.method.executePoolTask.operation;

import com.omgservers.service.service.task.impl.method.executePoolTask.dto.HandlePoolResult;
import io.smallrye.mutiny.Uni;

public interface UpdatePoolOperation {
    Uni<Void> execute(HandlePoolResult handlePoolResult);
}
