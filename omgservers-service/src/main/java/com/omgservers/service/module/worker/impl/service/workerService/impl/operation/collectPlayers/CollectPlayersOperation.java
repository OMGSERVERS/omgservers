package com.omgservers.service.module.worker.impl.service.workerService.impl.operation.collectPlayers;

import com.omgservers.model.player.PlayerModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import io.smallrye.mutiny.Uni;

import java.util.List;
import java.util.Map;

public interface CollectPlayersOperation {
    Uni<Map<Long, PlayerModel>> collectPlayers(List<RuntimeCommandModel> runtimeCommands);
}
