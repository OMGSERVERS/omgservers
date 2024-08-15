package com.omgservers.service.service.bootstrap;

import com.omgservers.service.service.testInterface.BootstrapServiceTestInterface;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@QuarkusTest
class BootstrapServiceTest {

    @Inject
    BootstrapServiceTestInterface bootstrapService;
}