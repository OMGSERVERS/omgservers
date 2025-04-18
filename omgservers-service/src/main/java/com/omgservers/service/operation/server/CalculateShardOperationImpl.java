package com.omgservers.service.operation.server;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.service.exception.ServerSideInternalException;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CalculateShardOperationImpl implements CalculateShardOperation {

    final GetServiceConfigOperation getServiceConfigOperation;
    final CalculateCrc16Operation calculateCrc16Operation;
    final GetIndexConfigOperation getIndexConfigOperation;

    @Override
    public Uni<ShardModel> execute(String... keys) {
        return execute(List.of(keys));
    }

    @Override
    public Uni<ShardModel> execute(List<String> keys) {
        return getIndexConfigOperation.execute()
                .map(indexConfig -> {
                    final var slot = execute(indexConfig.getTotalSlotsCount(), keys);
                    final var shardUriOptional = indexConfig.getShardUri(slot);
                    if (shardUriOptional.isEmpty()) {
                        throw new ServerSideInternalException(ExceptionQualifierEnum.INTERNAL_EXCEPTION_OCCURRED,
                                "failed to get shard uri");
                    }
                    final var shardUri = shardUriOptional.get();

                    final var thisUri = getServiceConfigOperation.getServiceConfig().server().uri();
                    final var foreign = !shardUri.equals(thisUri);
                    final var locked = indexConfig.getLockedSlots().contains(slot);

                    final var shardModel = new ShardModel(slot, shardUri, foreign, locked);
                    return shardModel;
                });
    }

    Integer execute(final Integer indexShardCount, final String... keys) {
        return execute(indexShardCount, List.of(keys));
    }

    Integer execute(final Integer indexShardCount, final List<String> keys) {
        final var hashKey = String.join("/", keys);
        final var keyBytes = hashKey.getBytes(StandardCharsets.UTF_8);
        final var crc16 = calculateCrc16Operation.execute(keyBytes);
        final var shard = crc16 % indexShardCount;
        return shard;
    }
}
