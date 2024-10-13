package com.omgservers.service.module.runtime.impl.service.runtimeService;

import com.omgservers.BaseTestClass;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@QuarkusTest
class RuntimeServiceTest extends BaseTestClass {

    @Inject
    RuntimeService runtimeService;
}