package com.omgservers.service.shard.matchmaker.operation;

import com.omgservers.BaseTestClass;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.service.shard.matchmaker.operation.testInterface.DeleteMatchmakerMatchAssignmentOperationTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class DeleteMatchmakerMatchAssignmentOperationTest extends BaseTestClass {

    @Inject
    DeleteMatchmakerMatchAssignmentOperationTestInterface deleteMatchmakerMatchAssignmentOperation;

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
    void givenMatchmakerMatchAssignment_whenDeleteMatchmakerMatchAssignment_thenDeleted() {
        final var matchmakerMatchAssignment = testData.getMatchmakerMatchAssignment();

        final var changeContext = deleteMatchmakerMatchAssignmentOperation.execute(
                matchmakerMatchAssignment.getMatchmakerId(),
                matchmakerMatchAssignment.getId());
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.MATCHMAKER_MATCH_ASSIGNMENT_DELETED));
    }

    @Test
    void givenUnknownIds_whenDeleteMatchmakerMatchAssignment_thenSkip() {
        final var matchmakerId = generateIdOperation.generateId();
        final var id = generateIdOperation.generateId();

        final var changeContext = deleteMatchmakerMatchAssignmentOperation.execute(matchmakerId, id);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.MATCHMAKER_MATCH_ASSIGNMENT_DELETED));
    }
}