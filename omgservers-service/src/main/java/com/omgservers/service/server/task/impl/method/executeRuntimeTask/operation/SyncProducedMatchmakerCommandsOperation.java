package com.omgservers.service.server.task.impl.method.executeRuntimeTask.operation;

import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandModel;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface SyncProducedMatchmakerCommandsOperation {
    Uni<Void> execute(List<MatchmakerCommandModel> producedMatchmakerCommands);
}
