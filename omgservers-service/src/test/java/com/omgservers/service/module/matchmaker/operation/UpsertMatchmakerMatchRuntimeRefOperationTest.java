package com.omgservers.service.module.matchmaker.operation;

import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchConfigModel;
import com.omgservers.schema.model.tenantVersion.TenantVersionGroupDto;
import com.omgservers.schema.model.tenantVersion.TenantVersionModeDto;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.matchmaker.MatchmakerMatchModelFactory;
import com.omgservers.service.factory.matchmaker.MatchmakerMatchRuntimeRefModelFactory;
import com.omgservers.service.factory.matchmaker.MatchmakerModelFactory;
import com.omgservers.service.module.matchmaker.operation.testInterface.UpsertMatchmakerMatchOperationTestInterface;
import com.omgservers.service.module.matchmaker.operation.testInterface.UpsertMatchmakerMatchRuntimeRefOperationTestInterface;
import com.omgservers.service.module.matchmaker.operation.testInterface.UpsertMatchmakerOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;

@Slf4j
@QuarkusTest
class UpsertMatchmakerMatchRuntimeRefOperationTest extends Assertions {

    @Inject
    UpsertMatchmakerOperationTestInterface upsertMatchmakerOperation;

    @Inject
    UpsertMatchmakerMatchOperationTestInterface upsertMatchmakerMatchOperationTestInterface;

    @Inject
    UpsertMatchmakerMatchRuntimeRefOperationTestInterface upsertMatchmakerMatchRuntimeRefOperation;

    @Inject
    MatchmakerModelFactory matchmakerModelFactory;

    @Inject
    MatchmakerMatchModelFactory matchmakerMatchModelFactory;

    @Inject
    MatchmakerMatchRuntimeRefModelFactory matchmakerMatchRuntimeRefModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenMatchmakerMatchRuntimeRef_whenUpsertMatchmakerMatchRuntimeRef_thenInserted() {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), versionId());
        upsertMatchmakerOperation.upsertMatchmaker(shard, matchmaker);

        final var modeConfig = TenantVersionModeDto.create(modeName(), 4, 8, new ArrayList<>() {{
            add(TenantVersionGroupDto.create("red", 1, 4));
            add(TenantVersionGroupDto.create("blue", 1, 4));
        }});
        final var matchmakerMatchConfig = new MatchmakerMatchConfigModel(modeConfig);
        final var matchmakerMatch = matchmakerMatchModelFactory.create(matchmaker.getId(), matchmakerMatchConfig);
        upsertMatchmakerMatchOperationTestInterface.upsertMatchmakerMatch(shard, matchmakerMatch);

        final var matchmakerMatchRuntimeRef = matchmakerMatchRuntimeRefModelFactory.create(matchmaker.getId(),
                matchmakerMatch.getId(),
                runtimeId());

        final var changeContext = upsertMatchmakerMatchRuntimeRefOperation.upsertMatchmakerMatchRuntimeRef(shard,
                matchmakerMatchRuntimeRef);
        assertTrue(changeContext.getResult());
    }

    @Test
    void givenMatchmakerMatchRuntimeRef_whenUpsertMatchmakerMatchRuntimeRef_thenUpdated() {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), versionId());
        upsertMatchmakerOperation.upsertMatchmaker(shard, matchmaker);

        final var modeConfig = TenantVersionModeDto.create(modeName(), 4, 8, new ArrayList<>() {{
            add(TenantVersionGroupDto.create("red", 1, 4));
            add(TenantVersionGroupDto.create("blue", 1, 4));
        }});
        final var matchmakerMatchConfig = new MatchmakerMatchConfigModel(modeConfig);
        final var matchmakerMatch = matchmakerMatchModelFactory.create(matchmaker.getId(), matchmakerMatchConfig);
        upsertMatchmakerMatchOperationTestInterface.upsertMatchmakerMatch(shard, matchmakerMatch);

        final var matchmakerMatchRuntimeRef = matchmakerMatchRuntimeRefModelFactory.create(matchmaker.getId(),
                matchmakerMatch.getId(),
                runtimeId());
        upsertMatchmakerMatchRuntimeRefOperation.upsertMatchmakerMatchRuntimeRef(shard, matchmakerMatchRuntimeRef);

        final var changeContext = upsertMatchmakerMatchRuntimeRefOperation.upsertMatchmakerMatchRuntimeRef(shard,
                matchmakerMatchRuntimeRef);
        assertFalse(changeContext.getResult());
    }

    @Test
    void givenUnknownIds_whenUpsertMatchmakerMatchRuntimeRef_thenException() {
        final var shard = 0;
        final var matchmakerMatchRuntimeRef = matchmakerMatchRuntimeRefModelFactory.create(matchmakerId(),
                matchId(),
                runtimeId());

        assertThrows(ServerSideBadRequestException.class, () -> upsertMatchmakerMatchRuntimeRefOperation
                .upsertMatchmakerMatchRuntimeRef(shard, matchmakerMatchRuntimeRef));
    }

    @Test
    void givenMatchmakerMatchRuntimeRef_whenUpsertMatchmakerMatchRuntimeRef_thenIdempotencyViolation() {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), versionId());
        upsertMatchmakerOperation.upsertMatchmaker(shard, matchmaker);

        final var modeConfig = TenantVersionModeDto.create(modeName(), 4, 8, new ArrayList<>() {{
            add(TenantVersionGroupDto.create("red", 1, 4));
            add(TenantVersionGroupDto.create("blue", 1, 4));
        }});
        final var matchmakerMatchConfig = new MatchmakerMatchConfigModel(modeConfig);
        final var matchmakerMatch = matchmakerMatchModelFactory.create(matchmaker.getId(), matchmakerMatchConfig);
        upsertMatchmakerMatchOperationTestInterface.upsertMatchmakerMatch(shard, matchmakerMatch);

        final var matchmakerMatchRuntimeRef1 = matchmakerMatchRuntimeRefModelFactory.create(matchmaker.getId(),
                matchmakerMatch.getId(),
                runtimeId());
        upsertMatchmakerMatchRuntimeRefOperation.upsertMatchmakerMatchRuntimeRef(shard, matchmakerMatchRuntimeRef1);

        final var matchmakerMatchRuntimeRef2 = matchmakerMatchRuntimeRefModelFactory.create(matchmaker.getId(),
                matchmakerMatch.getId(),
                runtimeId(),
                matchmakerMatchRuntimeRef1.getIdempotencyKey());

        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertMatchmakerMatchRuntimeRefOperation.upsertMatchmakerMatchRuntimeRef(shard,
                        matchmakerMatchRuntimeRef2));
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

    Long matchId() {
        return generateIdOperation.generateId();
    }

    Long runtimeId() {
        return generateIdOperation.generateId();
    }

    String modeName() {
        return "mode-" + UUID.randomUUID();
    }
}