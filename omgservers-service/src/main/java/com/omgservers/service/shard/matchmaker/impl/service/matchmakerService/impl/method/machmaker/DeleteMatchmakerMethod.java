package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.machmaker;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.matchmaker.matchmaker.DeleteMatchmakerRequest;
import com.omgservers.schema.module.matchmaker.matchmaker.DeleteMatchmakerResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteMatchmakerMethod {
    Uni<DeleteMatchmakerResponse> deleteMatchmaker(ShardModel shardModel, DeleteMatchmakerRequest request);
}
