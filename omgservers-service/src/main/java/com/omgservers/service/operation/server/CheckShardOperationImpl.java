package com.omgservers.service.operation.server;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.service.exception.ServerSideGoneException;
import com.omgservers.service.exception.ServerSideInternalException;
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
                        throw new ServerSideGoneException(ExceptionQualifierEnum.WRONG_SHARD,
                                "wrong shard server, shard=" + shard.shard());
                    }
                    if (shard.locked()) {
                        throw new ServerSideInternalException(ExceptionQualifierEnum.SHARD_LOCKED,
                                "shard is locked, shard=" + shard.shard());
                    }

                    return shard;
                });
    }
}
