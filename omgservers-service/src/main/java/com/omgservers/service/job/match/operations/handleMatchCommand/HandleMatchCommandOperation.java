package com.omgservers.service.job.match.operations.handleMatchCommand;

import com.omgservers.model.matchCommand.MatchCommandModel;
import io.smallrye.mutiny.Uni;

public interface HandleMatchCommandOperation {
    Uni<Void> handleMatchCommand(MatchCommandModel matchCommand);
}
