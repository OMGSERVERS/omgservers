package com.omgservers.service.shard.pool;

import com.omgservers.service.shard.pool.impl.service.poolService.PoolService;

public interface PoolShard {

    PoolService getService();
}
