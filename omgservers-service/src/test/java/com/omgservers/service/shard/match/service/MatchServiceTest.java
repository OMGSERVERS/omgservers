package com.omgservers.service.shard.match.service;

import com.omgservers.BaseTestClass;
import com.omgservers.service.shard.match.service.testInterface.MatchServiceTestInterface;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@QuarkusTest
class MatchServiceTest extends BaseTestClass {

    @Inject
    MatchServiceTestInterface matchService;
}