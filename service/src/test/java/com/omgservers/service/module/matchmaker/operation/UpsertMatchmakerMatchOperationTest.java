package com.omgservers.service.module.matchmaker.operation;

import com.omgservers.model.matchmakerMatch.MatchmakerMatchConfigModel;
import com.omgservers.model.version.VersionGroupModel;
import com.omgservers.model.version.VersionModeModel;
import com.omgservers.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.matchmaker.MatchmakerMatchModelFactory;
import com.omgservers.service.factory.matchmaker.MatchmakerModelFactory;
import com.omgservers.service.module.matchmaker.operation.testInterface.UpsertMatchmakerMatchOperationTestInterface;
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
class UpsertMatchmakerMatchOperationTest extends Assertions {

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
    void givenMatchmakerMatch_whenUpsertMatchmakerMatch_thenInserted() {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), versionId());
        upsertMatchmakerOperation.upsertMatchmaker(shard, matchmaker);

        final var modeConfig = VersionModeModel.create(modeName(), 4, 8, new ArrayList<>() {{
            add(VersionGroupModel.create("red", 1, 4));
            add(VersionGroupModel.create("blue", 1, 4));
        }});
        final var matchmakerMatchConfig = new MatchmakerMatchConfigModel(modeConfig);
        final var matchmakerMatch = matchmakerMatchModelFactory.create(matchmaker.getId(), matchmakerMatchConfig);
        final var changeContext = upsertMatchmakerMatchOperation.upsertMatchmakerMatch(shard, matchmakerMatch);
        assertTrue(changeContext.getResult());
    }

    @Test
    void givenMatchmakerMatch_whenUpsertMatchmakerMatch_thenUpdated() {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), versionId());
        upsertMatchmakerOperation.upsertMatchmaker(shard, matchmaker);

        final var modeConfig = VersionModeModel.create(modeName(), 4, 8, new ArrayList<>() {{
            add(VersionGroupModel.create("red", 1, 4));
            add(VersionGroupModel.create("blue", 1, 4));
        }});
        final var matchmakerMatchConfig = new MatchmakerMatchConfigModel(modeConfig);
        final var matchmakerMatch = matchmakerMatchModelFactory.create(matchmaker.getId(), matchmakerMatchConfig);
        upsertMatchmakerMatchOperation.upsertMatchmakerMatch(shard, matchmakerMatch);

        final var changeContext = upsertMatchmakerMatchOperation.upsertMatchmakerMatch(shard, matchmakerMatch);
        assertFalse(changeContext.getResult());
    }

    @Test
    void givenUnknownIds_whenUpsertMatchmakerMatch_thenException() {
        final var shard = 0;
        final var modeConfig = VersionModeModel.create(modeName(), 4, 8, new ArrayList<>() {{
            add(VersionGroupModel.create("red", 1, 4));
            add(VersionGroupModel.create("blue", 1, 4));
        }});
        final var matchmakerMatchConfig = new MatchmakerMatchConfigModel(modeConfig);
        final var matchmakerMatch = matchmakerMatchModelFactory.create(matchmakerId(), matchmakerMatchConfig);
        assertThrows(ServerSideBadRequestException.class, () ->
                upsertMatchmakerMatchOperation.upsertMatchmakerMatch(shard, matchmakerMatch));
    }

    @Test
    void givenMatchmakerRequest_whenUpsertMatchmakerRequest_thenIdempotencyViolation() {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), versionId());
        upsertMatchmakerOperation.upsertMatchmaker(shard, matchmaker);

        final var modeConfig = VersionModeModel.create(modeName(), 4, 8, new ArrayList<>() {{
            add(VersionGroupModel.create("red", 1, 4));
            add(VersionGroupModel.create("blue", 1, 4));
        }});
        final var matchmakerMatchConfig = new MatchmakerMatchConfigModel(modeConfig);
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