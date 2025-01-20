package com.omgservers.service.shard.root.impl.service.rootService;

import com.omgservers.BaseTestClass;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@QuarkusTest
class RootServiceTest extends BaseTestClass {

    @Inject
    RootService rootService;
}