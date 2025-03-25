package com.omgservers.service.shard.root.service;

import com.omgservers.BaseTestClass;
import com.omgservers.service.shard.root.service.testInterface.RootServiceTestInterface;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@QuarkusTest
class RootServiceTest extends BaseTestClass {

    @Inject
    RootServiceTestInterface rootService;
}