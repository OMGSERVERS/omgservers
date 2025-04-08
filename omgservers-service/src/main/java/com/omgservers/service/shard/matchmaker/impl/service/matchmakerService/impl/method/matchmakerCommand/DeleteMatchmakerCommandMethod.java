package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerCommand;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.matchmaker.matchmakerCommand.DeleteMatchmakerCommandRequest;
import com.omgservers.schema.module.matchmaker.matchmakerCommand.DeleteMatchmakerCommandResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteMatchmakerCommandMethod {
    Uni<DeleteMatchmakerCommandResponse> execute(ShardModel shardModel, DeleteMatchmakerCommandRequest request);
}
