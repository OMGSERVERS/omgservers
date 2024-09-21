package com.omgservers.service.module.matchmaker.operation;

import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchConfigModel;
import com.omgservers.schema.model.tenantVersion.TenantVersionGroupDto;
import com.omgservers.schema.model.tenantVersion.TenantVersionModeDto;
import com.omgservers.service.factory.matchmaker.MatchmakerMatchModelFactory;
import com.omgservers.service.factory.matchmaker.MatchmakerModelFactory;
import com.omgservers.service.module.matchmaker.operation.testInterface.DeleteMatchmakerMatchOperationTestInterface;
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
class DeleteClientRuntimeRefOperationTest extends Assertions {

    @Inject
    DeleteMatchmakerMatchOperationTestInterface deleteMatchOperation;

    @Inject
    UpsertMatchmakerOperationTestInterface upsertMatchmakerOperation;

    @Inject
    UpsertMatchmakerMatchOperationTestInterface upsertMatchmakerMatchOperation;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    MatchmakerModelFactory matchmakerModelFactory;

    @Inject
    MatchmakerMatchModelFactory matchmakerMatchModelFactory;

    @Test
    void givenMatchmakerMatch_whenDeleteMatchmakerMatch_thenDeleted() {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), versionId());
        upsertMatchmakerOperation.upsertMatchmaker(shard, matchmaker);

        final var modeConfig = TenantVersionModeDto.create(modeName(), 4, 8, new ArrayList<>() {{
            add(TenantVersionGroupDto.create("red", 1, 4));
            add(TenantVersionGroupDto.create("blue", 1, 4));
        }});
        final var matchConfig = new MatchmakerMatchConfigModel(modeConfig);
        final var match = matchmakerMatchModelFactory.create(matchmaker.getId(), matchConfig);
        upsertMatchmakerMatchOperation.upsertMatchmakerMatch(shard, match);

        final var changeContext = deleteMatchOperation.deleteMatchmakerMatch(shard, matchmaker.getId(), match.getId());
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.MATCHMAKER_MATCH_DELETED));
    }

    @Test
    void givenUnknownIds_whenDeleteMatchmakerMatch_thenSkip() {
        final var shard = 0;
        final var matchmakerId = generateIdOperation.generateId();
        final var id = generateIdOperation.generateId();

        final var changeContext = deleteMatchOperation.deleteMatchmakerMatch(shard, matchmakerId, id);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.MATCHMAKER_MATCH_DELETED));
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long versionId() {
        return generateIdOperation.generateId();
    }

    String modeName() {
        return "mode-" + UUID.randomUUID();
    }
}