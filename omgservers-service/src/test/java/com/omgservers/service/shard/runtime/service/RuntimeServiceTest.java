package com.omgservers.service.shard.runtime.service;

import com.omgservers.BaseTestClass;
import com.omgservers.service.shard.runtime.impl.service.runtimeService.RuntimeService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@QuarkusTest
class RuntimeServiceTest extends BaseTestClass {

    @Inject
    RuntimeService runtimeService;
}