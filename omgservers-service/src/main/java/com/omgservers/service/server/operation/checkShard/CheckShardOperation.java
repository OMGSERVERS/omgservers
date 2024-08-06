package com.omgservers.service.server.operation.checkShard;

import com.omgservers.schema.model.shard.ShardModel;
import io.smallrye.mutiny.Uni;

public interface CheckShardOperation {

    Uni<ShardModel> checkShard(String... keys);
}
