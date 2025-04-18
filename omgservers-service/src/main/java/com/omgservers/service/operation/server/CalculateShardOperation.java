package com.omgservers.service.operation.server;

import com.omgservers.schema.model.shard.ShardModel;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface CalculateShardOperation {

    Uni<ShardModel> execute(String... keys);

    Uni<ShardModel> execute(List<String> keys);
}
