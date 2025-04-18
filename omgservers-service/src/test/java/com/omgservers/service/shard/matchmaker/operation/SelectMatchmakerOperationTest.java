package com.omgservers.service.shard.matchmaker.operation;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.matchmaker.MatchmakerConfigDto;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.matchmaker.MatchmakerModelFactory;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.service.shard.matchmaker.operation.testInterface.SelectMatchmakerOperationTestInterface;
import com.omgservers.service.shard.matchmaker.operation.testInterface.UpsertMatchmakerOperationTestInterface;
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
        final var slot = 0;
        final var matchmaker1 = matchmakerModelFactory.create(tenantId(), versionId(), MatchmakerConfigDto.create());
        upsertMatchmakerOperation.upsertMatchmaker(slot, matchmaker1);

        final var matchmaker2 = selectMatchmakerOperation.selectMatchmaker(slot, matchmaker1.getId());
        assertEquals(matchmaker1, matchmaker2);
    }

    @Test
    void givenUnknownId_whenExecute_thenException() {
        final var slot = 0;
        final var id = generateIdOperation.generateId();

        assertThrows(ServerSideNotFoundException.class, () -> selectMatchmakerOperation
                .selectMatchmaker(slot, id));
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long versionId() {
        return generateIdOperation.generateId();
    }
}