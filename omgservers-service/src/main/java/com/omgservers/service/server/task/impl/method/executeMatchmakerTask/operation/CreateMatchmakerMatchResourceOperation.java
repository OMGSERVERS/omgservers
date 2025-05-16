package com.omgservers.service.server.task.impl.method.executeMatchmakerTask.operation;

import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceModel;
import com.omgservers.service.server.task.impl.method.executeMatchmakerTask.dto.FetchMatchmakerResult;
import com.omgservers.service.server.task.impl.method.executeMatchmakerTask.dto.HandleMatchmakerResult;

import java.util.Optional;

public interface CreateMatchmakerMatchResourceOperation {
    Optional<MatchmakerMatchResourceModel> execute(FetchMatchmakerResult fetchMatchmakerResult,
                                                   HandleMatchmakerResult handleMatchmakerResult,
                                                   String modeName);
}
