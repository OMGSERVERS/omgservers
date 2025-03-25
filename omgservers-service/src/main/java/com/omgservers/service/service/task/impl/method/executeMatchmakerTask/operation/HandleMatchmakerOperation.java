package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation;

import com.omgservers.service.service.task.impl.method.executeMatchmakerTask.dto.FetchMatchmakerResult;
import com.omgservers.service.service.task.impl.method.executeMatchmakerTask.dto.HandleMatchmakerResult;

public interface HandleMatchmakerOperation {
    HandleMatchmakerResult execute(FetchMatchmakerResult fetchMatchmakerResult);
}
