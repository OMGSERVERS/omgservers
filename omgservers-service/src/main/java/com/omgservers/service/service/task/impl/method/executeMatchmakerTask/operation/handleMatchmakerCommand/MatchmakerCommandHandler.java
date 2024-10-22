package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.handleMatchmakerCommand;

import com.omgservers.schema.model.matchmakerChangeOfState.MatchmakerChangeOfStateDto;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandQualifierEnum;
import com.omgservers.schema.model.matchmakerState.MatchmakerStateDto;

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
    void handle(MatchmakerStateDto currentState,
                MatchmakerChangeOfStateDto changeOfState,
                MatchmakerCommandModel matchmakerCommand);
}
