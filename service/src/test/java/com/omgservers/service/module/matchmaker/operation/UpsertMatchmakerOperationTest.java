package com.omgservers.service.module.matchmaker.operation;

import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.matchmaker.MatchmakerModelFactory;
import com.omgservers.service.module.matchmaker.operation.testInterface.UpsertMatchmakerOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertMatchmakerOperationTest extends Assertions {

    @Inject
    UpsertMatchmakerOperationTestInterface upsertMatchmakerOperation;

    @Inject
    MatchmakerModelFactory matchmakerModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenMatchmaker_whenUpsertMatchmaker_thenInserted() {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), stageId());
        final var changeContext = upsertMatchmakerOperation.upsertMatchmaker(shard, matchmaker);
        assertTrue(changeContext.getResult());
    }

    @Test
    void givenMatchmaker_whenUpsertMatchmaker_thenUpdated() {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), stageId());
        upsertMatchmakerOperation.upsertMatchmaker(shard, matchmaker);

        final var changeContext = upsertMatchmakerOperation.upsertMatchmaker(shard, matchmaker);
        assertFalse(changeContext.getResult());
    }

    @Test
    void givenMatchmaker_whenUpsertMatchmaker_thenIdempotencyViolation() {
        final var shard = 0;
        final var matchmaker1 = matchmakerModelFactory.create(tenantId(), stageId());
        upsertMatchmakerOperation.upsertMatchmaker(shard, matchmaker1);

        final var matchmaker2 = matchmakerModelFactory.create(tenantId(), stageId(), matchmaker1.getIdempotencyKey());
        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertMatchmakerOperation.upsertMatchmaker(shard, matchmaker2));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED, exception.getQualifier());
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long stageId() {
        return generateIdOperation.generateId();
    }
}