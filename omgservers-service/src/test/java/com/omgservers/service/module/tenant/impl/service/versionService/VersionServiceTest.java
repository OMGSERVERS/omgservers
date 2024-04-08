package com.omgservers.service.module.tenant.impl.service.versionService;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;

@Slf4j
@QuarkusTest
class VersionServiceTest extends Assertions {

    @Inject
    VersionService versionService;
}