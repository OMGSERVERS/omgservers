package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchResource;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchResource.GetMatchmakerMatchResourceRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchResource.GetMatchmakerMatchResourceResponse;
import io.smallrye.mutiny.Uni;

public interface GetMatchmakerMatchResourceMethod {
    Uni<GetMatchmakerMatchResourceResponse> execute(ShardModel shardModel, GetMatchmakerMatchResourceRequest request);
}
