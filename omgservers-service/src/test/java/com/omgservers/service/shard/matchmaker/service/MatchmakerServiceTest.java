package com.omgservers.service.shard.matchmaker.service;

import com.omgservers.BaseTestClass;
import com.omgservers.service.shard.matchmaker.service.testInterface.MatchmakerServiceTestInterface;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@QuarkusTest
class MatchmakerServiceTest extends BaseTestClass {

    @Inject
    MatchmakerServiceTestInterface matchmakerService;
}