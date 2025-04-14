package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchResource;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchResource.SyncMatchmakerMatchResourceRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchResource.SyncMatchmakerMatchResourceResponse;
import io.smallrye.mutiny.Uni;

public interface SyncMatchmakerMatchResourceMethod {
    Uni<SyncMatchmakerMatchResourceResponse> execute(ShardModel shardModel, SyncMatchmakerMatchResourceRequest request);
}
