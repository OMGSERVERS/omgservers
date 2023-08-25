package com.omgservers.base.impl.operation.checkShardOperation;

import com.omgservers.base.ShardModel;
import io.smallrye.mutiny.Uni;

public interface CheckShardOperation {

    Uni<ShardModel> checkShard(String... keys);
}
