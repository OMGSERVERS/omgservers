package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchResource;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.matchmaker.matchmakerMatchResource.ViewMatchmakerMatchResourcesRequest;
import com.omgservers.schema.module.matchmaker.matchmakerMatchResource.ViewMatchmakerMatchResourcesResponse;
import io.smallrye.mutiny.Uni;

public interface ViewMatchmakerMatchResourcesMethod {
    Uni<ViewMatchmakerMatchResourcesResponse> execute(ShardModel shardModel,
                                                      ViewMatchmakerMatchResourcesRequest request);
}
