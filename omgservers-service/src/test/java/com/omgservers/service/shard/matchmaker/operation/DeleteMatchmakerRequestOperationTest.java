package com.omgservers.service.shard.matchmaker.operation;

import com.omgservers.BaseTestClass;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.service.shard.matchmaker.operation.testInterface.DeleteMatchmakerRequestOperationTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class DeleteMatchmakerRequestOperationTest extends BaseTestClass {

    @Inject
    DeleteMatchmakerRequestOperationTestInterface deleteRequestOperation;

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
        final var model = testData.getMatchmakerRequest();

        final var changeContext = deleteRequestOperation
                .deleteMatchmakerRequest(model.getMatchmakerId(), model.getId());
        assertTrue(changeContext.getResult());
    }

    @Test
    void givenUnknownIds_whenDeleteRequest_thenSkip() {
        final var matchmakerId = generateIdOperation.generateId();
        final var id = generateIdOperation.generateId();

        final var changeContext = deleteRequestOperation.deleteMatchmakerRequest(matchmakerId, id);
        assertFalse(changeContext.getResult());
    }
}