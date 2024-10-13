package com.omgservers.service.module.pool.impl.service.poolService;

import com.omgservers.BaseTestClass;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@QuarkusTest
class PoolServiceTest extends BaseTestClass {

    @Inject
    PoolService poolService;
}