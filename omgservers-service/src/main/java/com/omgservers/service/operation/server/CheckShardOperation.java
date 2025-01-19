package com.omgservers.service.operation.server;

import com.omgservers.schema.model.shard.ShardModel;
import io.smallrye.mutiny.Uni;

public interface CheckShardOperation {

    Uni<ShardModel> checkShard(String... keys);
}
