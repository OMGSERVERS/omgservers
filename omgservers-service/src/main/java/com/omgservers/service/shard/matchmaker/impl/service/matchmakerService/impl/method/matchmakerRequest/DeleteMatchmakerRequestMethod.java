package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerRequest;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.matchmaker.matchmakerRequest.DeleteMatchmakerRequestRequest;
import com.omgservers.schema.module.matchmaker.matchmakerRequest.DeleteMatchmakerRequestResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteMatchmakerRequestMethod {
    Uni<DeleteMatchmakerRequestResponse> execute(ShardModel shardModel, DeleteMatchmakerRequestRequest request);
}
