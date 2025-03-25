package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.handleMatchmakerCommands;

import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandQualifierEnum;
import com.omgservers.service.service.task.impl.method.executeMatchmakerTask.dto.FetchMatchmakerResult;
import com.omgservers.service.service.task.impl.method.executeMatchmakerTask.dto.HandleMatchmakerResult;

public interface MatchmakerCommandHandler {

    MatchmakerCommandQualifierEnum getQualifier();

    boolean handle(FetchMatchmakerResult fetchMatchmakerResult,
                   HandleMatchmakerResult handleMatchmakerResult,
                   MatchmakerCommandModel matchmakerCommand);
}
