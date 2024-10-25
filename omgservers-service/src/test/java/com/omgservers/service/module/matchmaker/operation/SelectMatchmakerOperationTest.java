package com.omgservers.service.module.matchmaker.operation;

import com.omgservers.BaseTestClass;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.matchmaker.MatchmakerModelFactory;
import com.omgservers.service.module.matchmaker.operation.testInterface.SelectMatchmakerOperationTestInterface;
import com.omgservers.service.module.matchmaker.operation.testInterface.UpsertMatchmakerOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class SelectMatchmakerOperationTest extends BaseTestClass {

    @Inject
    SelectMatchmakerOperationTestInterface selectMatchmakerOperation;

    @Inject
    UpsertMatchmakerOperationTestInterface upsertMatchmakerOperation;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    MatchmakerModelFactory matchmakerModelFactory;

    @Test
    void givenMatchmaker_whenExecute_thenSelected() {
        final var shard = 0;
        final var matchmaker1 = matchmakerModelFactory.create(tenantId(), versionId());
        upsertMatchmakerOperation.upsertMatchmaker(shard, matchmaker1);

        final var matchmaker2 = selectMatchmakerOperation.selectMatchmaker(shard, matchmaker1.getId());
        assertEquals(matchmaker1, matchmaker2);
    }

    @Test
    void givenUnknownId_whenExecute_thenException() {
        final var shard = 0;
        final var id = generateIdOperation.generateId();

        assertThrows(ServerSideNotFoundException.class, () -> selectMatchmakerOperation
                .selectMatchmaker(shard, id));
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long versionId() {
        return generateIdOperation.generateId();
    }
}