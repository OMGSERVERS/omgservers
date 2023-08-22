package com.omgservers.application.operation.checkShardOperation;

import com.omgservers.application.ShardModel;
import com.omgservers.application.exception.ServerSideGoneException;
import com.omgservers.application.exception.ServerSideInternalException;
import com.omgservers.application.operation.calculateShardOperation.CalculateShardOperation;
import io.smallrye.mutiny.Uni;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

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
