package com.omgservers.service.module.pool;

import com.omgservers.service.module.pool.impl.service.dockerService.DockerService;
import com.omgservers.service.module.pool.impl.service.poolService.PoolService;

public interface PoolModule {

    PoolService getPoolService();

    DockerService getDockerService();
}
