package com.omgservers.service.operation.server;

import com.omgservers.schema.model.shard.ShardModel;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CalculateShardOperationImpl implements CalculateShardOperation {

    final GetServiceConfigOperation getServiceConfigOperation;
    final CalculateCrc16Operation calculateCrc16Operation;
    final GetIndexConfigOperation getIndexConfigOperation;

    @Override
    public Uni<ShardModel> calculateShard(String... keys) {
        return calculateShard(List.of(keys));
    }

    @Override
    public Uni<ShardModel> calculateShard(List<String> keys) {
        return getIndexConfigOperation.execute()
                .map(indexConfig -> {
                    final var shardIndex = calculateShard(indexConfig.getTotalShardCount(), keys);
                    final var shardServerUri = indexConfig.getServerUri(shardIndex);
                    final var thisServerUri = getServiceConfigOperation.getServiceConfig().server().uri();
                    final var foreign = !shardServerUri.equals(thisServerUri);
                    final var locked = indexConfig.getLockedShards().contains(shardIndex);

                    final var shardModel = new ShardModel(shardIndex, shardServerUri, foreign, locked);
                    return shardModel;
                });
    }

    @Override
    public Integer calculateShard(Integer indexShardCount, String... keys) {
        return calculateShard(indexShardCount, Arrays.asList(keys));
    }

    @Override
    public Integer calculateShard(Integer indexShardCount, List<String> keys) {
        if (indexShardCount == null) {
            throw new IllegalArgumentException("indexShardCount is null");
        }
        if (keys.isEmpty()) {
            throw new IllegalArgumentException("keys is empty");
        }

        final var hashKey = String.join("/", keys);
        final var keyBytes = hashKey.getBytes(StandardCharsets.UTF_8);
        final var crc16 = calculateCrc16Operation.calculateCrc16(keyBytes);
        final var shard = crc16 % indexShardCount;
        return shard;
    }
}
