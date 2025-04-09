package com.omgservers.service.server.task.impl.method.executeMatchmakerTask.operation;

import com.omgservers.service.server.task.impl.method.executeMatchmakerTask.dto.IndexMatchmakerResult;
import com.omgservers.service.server.task.impl.method.executeMatchmakerTask.dto.FetchMatchmakerResult;

public interface IndexMatchmakerOperation {
    IndexMatchmakerResult execute(FetchMatchmakerResult fetchMatchmakerResult);
}
