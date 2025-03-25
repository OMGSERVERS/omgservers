package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation;

import com.omgservers.service.service.task.impl.method.executeMatchmakerTask.dto.HandleMatchmakerResult;
import io.smallrye.mutiny.Uni;

public interface UpdateMatchmakerOperation {
    Uni<Void> execute(HandleMatchmakerResult handleMatchmakerResult);
}
