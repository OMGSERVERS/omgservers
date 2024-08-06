package com.omgservers.service.module.tenant.impl.service.stageService;

import com.omgservers.service.module.tenant.impl.service.stageService.StageService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;

@Slf4j
@QuarkusTest
class StageServiceTest extends Assertions {

    @Inject
    StageService stageService;
}