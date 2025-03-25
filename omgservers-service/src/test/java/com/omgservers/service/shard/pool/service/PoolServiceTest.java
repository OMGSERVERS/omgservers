package com.omgservers.service.shard.pool.service;

import com.omgservers.BaseTestClass;
import com.omgservers.service.shard.pool.service.testInterface.PoolServiceTestInterface;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@QuarkusTest
class PoolServiceTest extends BaseTestClass {

    @Inject
    PoolServiceTestInterface poolService;
}