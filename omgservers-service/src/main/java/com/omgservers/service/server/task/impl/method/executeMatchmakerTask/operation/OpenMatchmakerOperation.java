package com.omgservers.service.server.task.impl.method.executeMatchmakerTask.operation;

import com.omgservers.service.server.task.impl.method.executeMatchmakerTask.dto.FetchMatchmakerResult;
import io.smallrye.mutiny.Uni;

public interface OpenMatchmakerOperation {
    Uni<Void> execute(FetchMatchmakerResult fetchMatchmakerResult);
}
