package com.omgservers.service.module.matchmaker.operation;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchConfigDto;
import com.omgservers.schema.model.tenantVersion.TenantVersionGroupDto;
import com.omgservers.schema.model.tenantVersion.TenantVersionModeDto;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.matchmaker.MatchmakerMatchModelFactory;
import com.omgservers.service.factory.matchmaker.MatchmakerModelFactory;
import com.omgservers.service.module.matchmaker.operation.testInterface.UpsertMatchmakerMatchOperationTestInterface;
import com.omgservers.service.module.matchmaker.operation.testInterface.UpsertMatchmakerOperationTestInterface;
import com.omgservers.service.operation.server.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;

@Slf4j
@QuarkusTest
class UpsertMatchmakerMatchOperationTest extends BaseTestClass {

    @Inject
    UpsertMatchmakerOperationTestInterface upsertMatchmakerOperation;

    @Inject
    UpsertMatchmakerMatchOperationTestInterface upsertMatchmakerMatchOperation;

    @Inject
    MatchmakerModelFactory matchmakerModelFactory;

    @Inject
    MatchmakerMatchModelFactory matchmakerMatchModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenMatchmakerMatch_whenExecute_thenInserted() {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), versionId());
        upsertMatchmakerOperation.upsertMatchmaker(shard, matchmaker);

        final var modeConfig = TenantVersionModeDto.create(modeName(), 4, 8, new ArrayList<>() {{
            add(TenantVersionGroupDto.create("red", 1, 4));
            add(TenantVersionGroupDto.create("blue", 1, 4));
        }});
        final var matchmakerMatchConfig = new MatchmakerMatchConfigDto(modeConfig);
        final var matchmakerMatch = matchmakerMatchModelFactory.create(matchmaker.getId(), matchmakerMatchConfig);
        final var changeContext = upsertMatchmakerMatchOperation.upsertMatchmakerMatch(shard, matchmakerMatch);
        assertTrue(changeContext.getResult());
    }

    @Test
    void givenMatchmakerMatch_whenExecute_thenUpdated() {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), versionId());
        upsertMatchmakerOperation.upsertMatchmaker(shard, matchmaker);

        final var modeConfig = TenantVersionModeDto.create(modeName(), 4, 8, new ArrayList<>() {{
            add(TenantVersionGroupDto.create("red", 1, 4));
            add(TenantVersionGroupDto.create("blue", 1, 4));
        }});
        final var matchmakerMatchConfig = new MatchmakerMatchConfigDto(modeConfig);
        final var matchmakerMatch = matchmakerMatchModelFactory.create(matchmaker.getId(), matchmakerMatchConfig);
        upsertMatchmakerMatchOperation.upsertMatchmakerMatch(shard, matchmakerMatch);

        final var changeContext = upsertMatchmakerMatchOperation.upsertMatchmakerMatch(shard, matchmakerMatch);
        assertFalse(changeContext.getResult());
    }

    @Test
    void givenUnknownIds_whenExecute_thenException() {
        final var shard = 0;
        final var modeConfig = TenantVersionModeDto.create(modeName(), 4, 8, new ArrayList<>() {{
            add(TenantVersionGroupDto.create("red", 1, 4));
            add(TenantVersionGroupDto.create("blue", 1, 4));
        }});
        final var matchmakerMatchConfig = new MatchmakerMatchConfigDto(modeConfig);
        final var matchmakerMatch = matchmakerMatchModelFactory.create(matchmakerId(), matchmakerMatchConfig);
        assertThrows(ServerSideBadRequestException.class, () ->
                upsertMatchmakerMatchOperation.upsertMatchmakerMatch(shard, matchmakerMatch));
    }

    @Test
    void givenMatchmakerRequest_whenUpsertMatchmakerRequest_thenIdempotencyViolation() {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), versionId());
        upsertMatchmakerOperation.upsertMatchmaker(shard, matchmaker);

        final var modeConfig = TenantVersionModeDto.create(modeName(), 4, 8, new ArrayList<>() {{
            add(TenantVersionGroupDto.create("red", 1, 4));
            add(TenantVersionGroupDto.create("blue", 1, 4));
        }});
        final var matchmakerMatchConfig = new MatchmakerMatchConfigDto(modeConfig);
        final var matchmakerMatch1 = matchmakerMatchModelFactory.create(matchmaker.getId(), matchmakerMatchConfig);
        upsertMatchmakerMatchOperation.upsertMatchmakerMatch(shard, matchmakerMatch1);

        final var matchmakerMatch2 = matchmakerMatchModelFactory.create(matchmaker.getId(),
                matchmakerMatchConfig,
                matchmakerMatch1.getIdempotencyKey());
        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertMatchmakerMatchOperation.upsertMatchmakerMatch(shard, matchmakerMatch2));
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

    String modeName() {
        return "mode-" + UUID.randomUUID();
    }
}