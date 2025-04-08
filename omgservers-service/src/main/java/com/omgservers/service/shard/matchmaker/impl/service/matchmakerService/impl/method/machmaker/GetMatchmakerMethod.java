package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.machmaker;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.matchmaker.matchmaker.GetMatchmakerRequest;
import com.omgservers.schema.module.matchmaker.matchmaker.GetMatchmakerResponse;
import io.smallrye.mutiny.Uni;

public interface GetMatchmakerMethod {
    Uni<GetMatchmakerResponse> execute(ShardModel shardModel, GetMatchmakerRequest request);
}
