package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerRequest;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.matchmaker.matchmakerRequest.SyncMatchmakerRequestRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerRequest.SyncMatchmakerRequestResponse;
import io.smallrye.mutiny.Uni;

public interface SyncMatchmakerRequestMethod {
    Uni<SyncMatchmakerRequestResponse> execute(ShardModel shardModel, SyncMatchmakerRequestRequest request);
}
