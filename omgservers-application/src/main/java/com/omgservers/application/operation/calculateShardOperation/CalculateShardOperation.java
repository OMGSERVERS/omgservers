package com.omgservers.application.operation.calculateShardOperation;

import com.omgservers.application.model.ShardModel;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface CalculateShardOperation {

    Uni<ShardModel> calculateShard(String... keys);

    Uni<ShardModel> calculateShard(List<String> keys);

    Uni<ShardModel> calculateShard(String indexName, List<String> keys);

    Integer calculateShard(Integer indexShardCount, String... keys);

    Integer calculateShard(Integer indexShardCount, List<String> keys);
}
