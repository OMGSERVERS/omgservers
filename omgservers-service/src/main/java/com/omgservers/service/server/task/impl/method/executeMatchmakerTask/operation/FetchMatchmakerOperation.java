package com.omgservers.service.server.task.impl.method.executeMatchmakerTask.operation;

import com.omgservers.service.server.task.impl.method.executeMatchmakerTask.dto.FetchMatchmakerResult;
import io.smallrye.mutiny.Uni;

public interface FetchMatchmakerOperation {
    Uni<FetchMatchmakerResult> execute(Long matchmakerId);
}
