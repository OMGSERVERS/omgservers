package com.omgservers.service.module.matchmaker.impl.service.matchmakerService;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;

@Slf4j
@QuarkusTest
class MatchmakerServiceTest extends Assertions {

    @Inject
    MatchmakerService matchmakerService;
}