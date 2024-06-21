package com.omgservers.service.module.pool.impl.service.poolService;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;

@Slf4j
@QuarkusTest
class PoolServiceTest extends Assertions {

    @Inject
    PoolService poolService;
}