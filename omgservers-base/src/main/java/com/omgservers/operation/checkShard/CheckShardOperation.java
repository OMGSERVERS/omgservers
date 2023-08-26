package com.omgservers.operation.checkShard;

import com.omgservers.model.shard.ShardModel;
import io.smallrye.mutiny.Uni;

public interface CheckShardOperation {

    Uni<ShardModel> checkShard(String... keys);
}
