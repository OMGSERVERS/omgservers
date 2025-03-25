package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation;

import com.omgservers.service.service.task.impl.method.executeMatchmakerTask.dto.IndexMatchmakerResult;
import com.omgservers.service.service.task.impl.method.executeMatchmakerTask.dto.FetchMatchmakerResult;

public interface IndexMatchmakerOperation {
    IndexMatchmakerResult execute(FetchMatchmakerResult fetchMatchmakerResult);
}
