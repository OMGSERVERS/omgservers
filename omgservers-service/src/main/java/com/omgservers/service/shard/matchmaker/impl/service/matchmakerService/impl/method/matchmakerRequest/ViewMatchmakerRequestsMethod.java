package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerRequest;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.matchmaker.matchmakerRequest.ViewMatchmakerRequestsRequest;
import com.omgservers.schema.module.matchmaker.matchmakerRequest.ViewMatchmakerRequestsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewMatchmakerRequestsMethod {
    Uni<ViewMatchmakerRequestsResponse> execute(ShardModel shardModel, ViewMatchmakerRequestsRequest request);
}
