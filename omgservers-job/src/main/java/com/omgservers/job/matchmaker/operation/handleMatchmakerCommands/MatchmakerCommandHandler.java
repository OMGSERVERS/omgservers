package com.omgservers.job.matchmaker.operation.handleMatchmakerCommands;

import com.omgservers.model.matchmakerChangeOfState.MatchmakerChangeOfState;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandQualifierEnum;
import com.omgservers.model.matchmakerState.IndexedMatchmakerState;

public interface MatchmakerCommandHandler {

    /**
     * Implementation should provide the matchmaker command qualifier they are intended for.
     *
     * @return matchmaker command qualifiers
     */
    MatchmakerCommandQualifierEnum getQualifier();

    /**
     * Handle matchmaker command by handler.
     *
     * @param indexedMatchmakerState  indexed state of matchmaker
     * @param matchmakerChangeOfState container to collect state changes for further synchronization
     * @param matchmakerCommand       command to process by this handler
     */
    void handle(IndexedMatchmakerState indexedMatchmakerState,
                MatchmakerChangeOfState matchmakerChangeOfState,
                MatchmakerCommandModel matchmakerCommand);
}
