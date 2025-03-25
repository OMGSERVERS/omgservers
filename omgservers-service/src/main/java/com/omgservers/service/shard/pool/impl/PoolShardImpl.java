package com.omgservers.service.shard.pool.impl;

import com.omgservers.service.shard.pool.PoolShard;
import com.omgservers.service.shard.pool.impl.service.poolService.PoolService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class PoolShardImpl implements PoolShard {

    final PoolService poolService;

    @Override
    public PoolService getService() {
        return poolService;
    }
}
