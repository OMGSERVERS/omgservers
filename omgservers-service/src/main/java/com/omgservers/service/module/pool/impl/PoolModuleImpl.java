package com.omgservers.service.module.pool.impl;

import com.omgservers.service.module.pool.PoolModule;
import com.omgservers.service.module.pool.impl.service.dockerService.DockerService;
import com.omgservers.service.module.pool.impl.service.poolService.PoolService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class PoolModuleImpl implements PoolModule {

    final PoolService poolService;

    final DockerService dockerService;
}
