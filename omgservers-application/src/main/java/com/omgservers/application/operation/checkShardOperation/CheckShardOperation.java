package com.omgservers.application.operation.checkShardOperation;

import com.omgservers.application.ShardModel;
import io.smallrye.mutiny.Uni;

public interface CheckShardOperation {

    Uni<ShardModel> checkShard(String... keys);
}
