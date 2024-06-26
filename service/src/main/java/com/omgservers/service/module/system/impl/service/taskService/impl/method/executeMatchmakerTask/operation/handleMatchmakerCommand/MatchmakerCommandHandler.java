package com.omgservers.service.module.system.impl.service.taskService.impl.method.executeMatchmakerTask.operation.handleMatchmakerCommand;

import com.omgservers.model.matchmakerChangeOfState.MatchmakerChangeOfStateModel;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandQualifierEnum;
import com.omgservers.model.matchmakerState.MatchmakerStateModel;

public interface MatchmakerCommandHandler {

    /**
     * Implementation should provide the matchmaker command qualifier they are intended for.
     *
     * @return matchmaker command qualifier
     */
    MatchmakerCommandQualifierEnum getQualifier();

    /**
     * Handle matchmaker command by this handler.
     *
     * @param currentState      current matchmaker state
     * @param changeOfState     container for state changes
     * @param matchmakerCommand matchmaker command to process by this handler
     */
    void handle(MatchmakerStateModel currentState,
                MatchmakerChangeOfStateModel changeOfState,
                MatchmakerCommandModel matchmakerCommand);
}
