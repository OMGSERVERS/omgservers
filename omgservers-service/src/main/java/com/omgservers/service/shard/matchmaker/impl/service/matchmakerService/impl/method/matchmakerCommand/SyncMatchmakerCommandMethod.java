package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerCommand;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.matchmaker.matchmakerCommand.SyncMatchmakerCommandRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerCommand.SyncMatchmakerCommandResponse;
import io.smallrye.mutiny.Uni;

public interface SyncMatchmakerCommandMethod {
    Uni<SyncMatchmakerCommandResponse> execute(ShardModel shardModel, SyncMatchmakerCommandRequest request);
}
