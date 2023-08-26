package com.omgservers.base.operation.calculateShard;

import com.omgservers.base.module.internal.InternalModule;
import com.omgservers.base.operation.calculateCrc16.CalculateCrc16Operation;
import com.omgservers.base.operation.getConfig.GetConfigOperation;
import com.omgservers.dto.internalModule.GetIndexHelpResponse;
import com.omgservers.dto.internalModule.GetIndexRequest;
import com.omgservers.model.shard.ShardModel;
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

    final InternalModule internalModule;

    final CalculateCrc16Operation calculateCrc16Operation;
    final GetConfigOperation getConfigOperation;

    @Override
    public Uni<ShardModel> calculateShard(String... keys) {
        return calculateShard(Arrays.asList(keys));
    }

    @Override
    public Uni<ShardModel> calculateShard(List<String> keys) {
        final var indexName = getConfigOperation.getConfig().indexName();
        return calculateShard(indexName, keys);
    }

    @Override
    public Uni<ShardModel> calculateShard(String indexName, List<String> keys) {
        final var getIndexInternalRequest = new GetIndexRequest(indexName);
        return internalModule.getIndexService().getIndex(getIndexInternalRequest)
                .map(GetIndexHelpResponse::getIndex)
                .map(index -> {
                    final var shardIndex = calculateShard(index.getConfig().getTotalShardCount(), keys);
                    final var shardServerUri = index.getConfig().getServerUri(shardIndex);
                    final var thisServerUri = getConfigOperation.getConfig().serverUri();
                    final var foreign = !shardServerUri.equals(thisServerUri);
                    final var locked = index.getConfig().getLockedShards().contains(shardIndex);

                    final var shardModel = new ShardModel(shardIndex, shardServerUri, foreign, locked);
                    log.debug("Shard was calculated, shard={}, keys={}", shardModel, keys);
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
