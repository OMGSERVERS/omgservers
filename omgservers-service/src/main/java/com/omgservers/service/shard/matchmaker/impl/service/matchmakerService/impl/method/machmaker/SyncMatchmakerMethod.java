package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.machmaker;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.matchmaker.matchmaker.SyncMatchmakerRequest;
import com.omgservers.schema.shard.matchmaker.matchmaker.SyncMatchmakerResponse;
import io.smallrye.mutiny.Uni;

public interface SyncMatchmakerMethod {
    Uni<SyncMatchmakerResponse> execute(ShardModel shardModel, SyncMatchmakerRequest request);
}
