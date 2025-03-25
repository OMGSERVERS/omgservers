package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.handleMatchmakerCommands;

import com.omgservers.service.service.task.impl.method.executeMatchmakerTask.dto.FetchMatchmakerResult;
import com.omgservers.service.service.task.impl.method.executeMatchmakerTask.dto.HandleMatchmakerResult;

public interface HandleMatchmakerCommandsOperation {
    void execute(FetchMatchmakerResult fetchMatchmakerResult,
                 HandleMatchmakerResult handleMatchmakerResult);
}
