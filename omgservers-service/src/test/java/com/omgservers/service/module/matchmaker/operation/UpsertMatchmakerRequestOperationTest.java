package com.omgservers.service.module.matchmaker.operation;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.request.MatchmakerRequestConfigDto;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.matchmaker.MatchmakerModelFactory;
import com.omgservers.service.factory.matchmaker.MatchmakerRequestModelFactory;
import com.omgservers.service.module.matchmaker.operation.testInterface.UpsertMatchmakerOperationTestInterface;
import com.omgservers.service.module.matchmaker.operation.testInterface.UpsertMatchmakerRequestOperationTestInterface;
import com.omgservers.service.operation.server.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertMatchmakerRequestOperationTest extends BaseTestClass {

    @Inject
    UpsertMatchmakerOperationTestInterface upsertMatchmakerOperation;

    @Inject
    UpsertMatchmakerRequestOperationTestInterface upsertMatchmakerRequestOperation;

    @Inject
    MatchmakerModelFactory matchmakerModelFactory;

    @Inject
    MatchmakerRequestModelFactory matchmakerRequestModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenMatchmakerRequest_whenExecute_thenInserted() {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), versionId());
        upsertMatchmakerOperation.upsertMatchmaker(shard, matchmaker);

        final var matchmakerRequest = matchmakerRequestModelFactory.create(matchmaker.getId(),
                userId(),
                clientId(),
                "mode",
                MatchmakerRequestConfigDto.create());

        final var changeContext = upsertMatchmakerRequestOperation.upsertMatchmakerRequest(shard, matchmakerRequest);
        assertTrue(changeContext.getResult());
    }

    @Test
    void givenMatchmakerRequest_whenExecute_thenUpdated() {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), versionId());
        upsertMatchmakerOperation.upsertMatchmaker(shard, matchmaker);

        final var matchmakerRequest = matchmakerRequestModelFactory.create(matchmaker.getId(),
                userId(),
                clientId(),
                "mode",
                MatchmakerRequestConfigDto.create());
        upsertMatchmakerRequestOperation.upsertMatchmakerRequest(shard, matchmakerRequest);

        final var changeContext = upsertMatchmakerRequestOperation.upsertMatchmakerRequest(shard, matchmakerRequest);
        assertFalse(changeContext.getResult());
    }

    @Test
    void givenUnknownIds_whenExecute_thenException() {
        final var shard = 0;
        final var matchmakerRequest = matchmakerRequestModelFactory.create(matchmakerId(),
                userId(),
                clientId(),
                "mode",
                MatchmakerRequestConfigDto.create());
        assertThrows(ServerSideBadRequestException.class, () ->
                upsertMatchmakerRequestOperation.upsertMatchmakerRequest(shard, matchmakerRequest));
    }

    @Test
    void givenMatchmakerRequest_whenExecute_thenIdempotencyViolation() {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), versionId());
        upsertMatchmakerOperation.upsertMatchmaker(shard, matchmaker);

        final var request1 = matchmakerRequestModelFactory.create(matchmaker.getId(),
                userId(),
                clientId(),
                "mode",
                MatchmakerRequestConfigDto.create());
        upsertMatchmakerRequestOperation.upsertMatchmakerRequest(shard, request1);

        final var request2 = matchmakerRequestModelFactory.create(matchmaker.getId(),
                userId(),
                clientId(),
                "mode",
                MatchmakerRequestConfigDto.create(),
                request1.getIdempotencyKey());
        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertMatchmakerRequestOperation.upsertMatchmakerRequest(shard, request2));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED, exception.getQualifier());
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long versionId() {
        return generateIdOperation.generateId();
    }

    Long matchmakerId() {
        return generateIdOperation.generateId();
    }

    Long userId() {
        return generateIdOperation.generateId();
    }

    Long clientId() {
        return generateIdOperation.generateId();
    }
}