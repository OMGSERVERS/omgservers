package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerCommand;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.matchmaker.matchmakerCommand.ViewMatchmakerCommandsRequest;
import com.omgservers.schema.module.matchmaker.matchmakerCommand.ViewMatchmakerCommandsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewMatchmakerCommandsMethod {
    Uni<ViewMatchmakerCommandsResponse> execute(ShardModel shardModel, ViewMatchmakerCommandsRequest request);
}
