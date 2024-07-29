package com.omgservers.service.server.service.task.impl.method.executeMatchmakerTask.operation.handleMatchmakerCommand;

import com.omgservers.schema.model.matchmakerChangeOfState.MatchmakerChangeOfStateModel;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandQualifierEnum;
import com.omgservers.schema.model.matchmakerState.MatchmakerStateModel;

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
