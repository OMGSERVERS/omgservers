package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchResource;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchResource.UpdateMatchmakerMatchResourceStatusRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchResource.UpdateMatchmakerMatchResourceStatusResponse;
import io.smallrye.mutiny.Uni;

public interface UpdateMatchmakerMatchResourceStatusMethod {
    Uni<UpdateMatchmakerMatchResourceStatusResponse> execute(ShardModel shardModel,
                                                             UpdateMatchmakerMatchResourceStatusRequest request);
}
