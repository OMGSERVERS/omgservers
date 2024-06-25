package com.omgservers.service.operation.calculateShard;

import com.omgservers.model.shard.ShardModel;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface CalculateShardOperation {

    Uni<ShardModel> calculateShard(String... keys);

    Uni<ShardModel> calculateShard(List<String> keys);

    Uni<ShardModel> calculateShard(Long indexId, List<String> keys);

    Integer calculateShard(Integer indexShardCount, String... keys);

    Integer calculateShard(Integer indexShardCount, List<String> keys);
}
