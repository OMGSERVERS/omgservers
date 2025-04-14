package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchResource;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchResource.DeleteMatchmakerMatchResourceRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchResource.DeleteMatchmakerMatchResourceResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteMatchmakerMatchResourceMethod {
    Uni<DeleteMatchmakerMatchResourceResponse> execute(ShardModel shardModel,
                                                       DeleteMatchmakerMatchResourceRequest request);
}
