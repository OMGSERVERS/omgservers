package com.omgservers.service.shard.matchmaker.operation;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.matchmaker.MatchmakerConfigDto;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.matchmaker.MatchmakerModelFactory;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.service.shard.matchmaker.operation.testInterface.UpsertMatchmakerOperationTestInterface;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertMatchmakerOperationTest extends BaseTestClass {

    @Inject
    UpsertMatchmakerOperationTestInterface upsertMatchmakerOperation;

    @Inject
    MatchmakerModelFactory matchmakerModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenMatchmaker_whenExecute_thenInserted() {
        final var slot = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), stageId(), MatchmakerConfigDto.create());
        final var changeContext = upsertMatchmakerOperation.upsertMatchmaker(slot, matchmaker);
        assertTrue(changeContext.getResult());
    }

    @Test
    void givenMatchmaker_whenExecute_thenUpdated() {
        final var slot = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), stageId(), MatchmakerConfigDto.create());
        upsertMatchmakerOperation.upsertMatchmaker(slot, matchmaker);

        final var changeContext = upsertMatchmakerOperation.upsertMatchmaker(slot, matchmaker);
        assertFalse(changeContext.getResult());
    }

    @Test
    void givenMatchmaker_whenExecute_thenIdempotencyViolation() {
        final var slot = 0;
        final var matchmaker1 = matchmakerModelFactory.create(tenantId(), stageId(), MatchmakerConfigDto.create());
        upsertMatchmakerOperation.upsertMatchmaker(slot, matchmaker1);

        final var matchmaker2 = matchmakerModelFactory.create(tenantId(), stageId(), MatchmakerConfigDto.create(),
                matchmaker1.getIdempotencyKey());
        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertMatchmakerOperation.upsertMatchmaker(slot, matchmaker2));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED, exception.getQualifier());
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long stageId() {
        return generateIdOperation.generateId();
    }
}