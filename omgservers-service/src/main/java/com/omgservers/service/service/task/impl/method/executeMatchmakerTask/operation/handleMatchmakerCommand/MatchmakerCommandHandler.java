package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.handleMatchmakerCommand;

import com.omgservers.schema.model.matchmakerChangeOfState.MatchmakerChangeOfStateDto;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandQualifierEnum;
import com.omgservers.schema.model.matchmakerState.MatchmakerStateDto;

public interface MatchmakerCommandHandler {

    MatchmakerCommandQualifierEnum getQualifier();

    void handle(MatchmakerStateDto matchmakerState,
                MatchmakerChangeOfStateDto matchmakerChangeOfState,
                MatchmakerCommandModel matchmakerCommand);
}
