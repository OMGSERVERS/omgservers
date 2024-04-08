package com.omgservers.service.module.runtime.impl.service.runtimeService;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;

@Slf4j
@QuarkusTest
class RuntimeServiceTest extends Assertions {

    @Inject
    RuntimeService runtimeService;
}