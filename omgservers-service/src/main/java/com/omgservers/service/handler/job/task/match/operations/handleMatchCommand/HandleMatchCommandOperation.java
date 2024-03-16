package com.omgservers.service.handler.job.task.match.operations.handleMatchCommand;

import com.omgservers.model.matchCommand.MatchmakerMatchCommandModel;
import io.smallrye.mutiny.Uni;

public interface HandleMatchCommandOperation {
    Uni<Void> handleMatchCommand(MatchmakerMatchCommandModel matchCommand);
}
