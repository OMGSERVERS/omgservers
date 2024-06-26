package com.omgservers.service.operation.calculateShard;

import com.omgservers.model.dto.system.GetIndexRequest;
import com.omgservers.model.dto.system.GetIndexResponse;
import com.omgservers.model.index.IndexModel;
import com.omgservers.model.shard.ShardModel;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.operation.calculateCrc16.CalculateCrc16Operation;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
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

    final SystemModule systemModule;

    final CalculateCrc16Operation calculateCrc16Operation;
    final GetConfigOperation getConfigOperation;

    @Override
    public Uni<ShardModel> calculateShard(String... keys) {
        return calculateShard(List.of(keys));
    }

    @Override
    public Uni<ShardModel> calculateShard(List<String> keys) {
        final var indexId = getConfigOperation.getServiceConfig().defaults().indexId();
        return calculateShard(indexId, keys);
    }

    @Override
    public Uni<ShardModel> calculateShard(Long indexId, List<String> keys) {
        return getIndex(indexId)
                .map(index -> {
                    final var shardIndex = calculateShard(index.getConfig().getTotalShardCount(), keys);
                    final var shardServerUri = index.getConfig().getServerUri(shardIndex);
                    final var thisServerUri = getConfigOperation.getServiceConfig().index().serverUri();
                    final var foreign = !shardServerUri.equals(thisServerUri);
                    final var locked = index.getConfig().getLockedShards().contains(shardIndex);

                    final var shardModel = new ShardModel(shardIndex, shardServerUri, foreign, locked);
                    return shardModel;
                });
    }

    Uni<IndexModel> getIndex(final Long indexId) {
        final var request = new GetIndexRequest(indexId);
        return systemModule.getIndexService().getIndex(request)
                .map(GetIndexResponse::getIndex);
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
