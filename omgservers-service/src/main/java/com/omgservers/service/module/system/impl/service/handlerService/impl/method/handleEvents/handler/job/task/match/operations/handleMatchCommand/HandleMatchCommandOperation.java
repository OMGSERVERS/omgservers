package com.omgservers.service.module.system.impl.service.handlerService.impl.method.handleEvents.handler.job.task.match.operations.handleMatchCommand;

import com.omgservers.model.matchCommand.MatchCommandModel;
import io.smallrye.mutiny.Uni;

public interface HandleMatchCommandOperation {
    Uni<Void> handleMatchCommand(MatchCommandModel matchCommand);
}
