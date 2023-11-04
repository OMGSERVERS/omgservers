package com.omgservers.service.job.match.operations.handleMatchCommand;

import com.omgservers.model.matchCommand.MatchCommandModel;
import com.omgservers.model.matchCommand.MatchCommandQualifierEnum;
import io.smallrye.mutiny.Uni;

public interface MatchCommandHandler {

    /**
     * Implementation should provide the match command qualifier they are intended for.
     *
     * @return match command qualifier
     */
    MatchCommandQualifierEnum getQualifier();

    /**
     * Handle match command by this handler.
     *
     * @param matchCommand match command to process by this handler
     */
    Uni<Void> handle(MatchCommandModel matchCommand);
}
