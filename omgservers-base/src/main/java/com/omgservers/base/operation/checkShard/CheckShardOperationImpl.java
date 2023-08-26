package com.omgservers.base.operation.checkShard;

import com.omgservers.model.shard.ShardModel;
import com.omgservers.base.operation.calculateShard.CalculateShardOperation;
import com.omgservers.exception.ServerSideGoneException;
import com.omgservers.exception.ServerSideInternalException;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CheckShardOperationImpl implements CheckShardOperation {

    final CalculateShardOperation calculateShardOperation;

    @Override
    public Uni<ShardModel> checkShard(String... keys) {
        return calculateShardOperation.calculateShard(keys)
                .map(shard -> {
                    if (shard.foreign()) {
                        throw new ServerSideGoneException("wrong shard server, shard=" + shard.shard());
                    }
                    if (shard.locked()) {
                        throw new ServerSideInternalException("shard is locked, shard=" + shard.shard());
                    }

                    return shard;
                });
    }
}
