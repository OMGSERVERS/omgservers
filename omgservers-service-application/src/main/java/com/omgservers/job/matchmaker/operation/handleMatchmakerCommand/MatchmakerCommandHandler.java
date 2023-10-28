package com.omgservers.job.matchmaker.operation.handleMatchmakerCommand;

import com.omgservers.model.matchmakerChangeOfState.MatchmakerChangeOfState;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandQualifierEnum;
import com.omgservers.model.matchmakerState.MatchmakerState;
import io.smallrye.mutiny.Uni;

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
     * @param matchmakerState   current matchmaker state
     * @param changeOfState     container for state changes
     * @param matchmakerCommand matchmaker command to process by this handler
     */
    Uni<Void> handle(MatchmakerState matchmakerState,
                     MatchmakerChangeOfState changeOfState,
                     MatchmakerCommandModel matchmakerCommand);
}
