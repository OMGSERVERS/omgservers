package com.omgservers.service.server.task.impl.method.executeMatchmakerTask.operation;

import com.omgservers.service.server.task.impl.method.executeMatchmakerTask.dto.FetchMatchmakerResult;
import com.omgservers.service.server.task.impl.method.executeMatchmakerTask.dto.HandleMatchmakerResult;

public interface HandleMatchmakerRequestsOperation {
    void execute(FetchMatchmakerResult fetchMatchmakerResult,
                 HandleMatchmakerResult handleMatchmakerResult);
}
