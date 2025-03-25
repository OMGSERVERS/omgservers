package com.omgservers.service.shard.matchmaker.operation;

import com.omgservers.BaseTestClass;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.service.shard.matchmaker.operation.testInterface.SelectMatchmakerRequestOperationTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class SelectMatchmakerRequestOperationTest extends BaseTestClass {

    @Inject
    SelectMatchmakerRequestOperationTestInterface selectMatchmakerRequestOperation;

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
    void givenModel_whenExecute_thenSelected() {
        final var model1 = testData.getMatchmakerRequest();

        final var model2 = selectMatchmakerRequestOperation
                .selectMatchmakerRequest(model1.getMatchmakerId(), model1.getId());
        assertEquals(model1, model2);
    }

    @Test
    void givenUnknownIds_whenExecute_thenException() {
        final var matchmakerId = generateIdOperation.generateId();
        final var id = generateIdOperation.generateId();

        assertThrows(ServerSideNotFoundException.class, () -> selectMatchmakerRequestOperation
                .selectMatchmakerRequest(matchmakerId, id));
    }
}