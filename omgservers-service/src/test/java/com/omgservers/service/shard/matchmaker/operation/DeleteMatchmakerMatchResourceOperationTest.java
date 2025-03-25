package com.omgservers.service.shard.matchmaker.operation;

import com.omgservers.BaseTestClass;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.service.shard.matchmaker.operation.testInterface.DeleteMatchmakerMatchResourceOperationTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class DeleteMatchmakerMatchResourceOperationTest extends BaseTestClass {

    @Inject
    DeleteMatchmakerMatchResourceOperationTestInterface deleteMatchmakerMatchResourceOperation;

    @Inject
    GenerateIdOperation generateIdOperation;
    @Inject
    TestDataFactory testDataFactory;

    TestDataFactory.DefaultTestData testData;

    @BeforeEach
    void beforeEach() {
        testData = testDataFactory.createDefaultTestData();
    }

    @Test
    void givenModel_whenExecute_thenDeleted() {
        final var model = testData.getMatchmakerMatchResource();

        final var changeContext = deleteMatchmakerMatchResourceOperation.execute(model.getMatchmakerId(), model.getId());
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.MATCHMAKER_MATCH_RESOURCE_DELETED));
    }

    @Test
    void givenModel_whenExecute_thenSkip() {
        final var matchmakerId = generateIdOperation.generateId();
        final var id = generateIdOperation.generateId();

        final var changeContext = deleteMatchmakerMatchResourceOperation.execute(matchmakerId, id);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.MATCHMAKER_MATCH_RESOURCE_DELETED));
    }
}