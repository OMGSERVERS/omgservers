package com.omgservers.service.module.matchmaker.impl.service.matchmakerService;

import com.omgservers.BaseTestClass;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@QuarkusTest
class MatchmakerServiceTest extends BaseTestClass {

    @Inject
    MatchmakerService matchmakerService;
}