package com.omgservers.service.module.matchmaker.operation;

import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.matchmakerMatch.MatchmakerMatchConfigModel;
import com.omgservers.model.version.VersionGroupModel;
import com.omgservers.model.version.VersionModeModel;
import com.omgservers.service.factory.MatchmakerMatchModelFactory;
import com.omgservers.service.factory.MatchmakerModelFactory;
import com.omgservers.service.module.matchmaker.impl.operation.upsertMatchmakerMatch.UpsertMatchmakerMatchOperation;
import com.omgservers.service.module.matchmaker.impl.operation.upsertMatchmaker.UpsertMatchmakerOperation;
import com.omgservers.service.module.matchmaker.operation.testInterface.DeleteMatchmakerMatchOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;

@Slf4j
@QuarkusTest
class DeleteClientRuntimeRefOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    DeleteMatchmakerMatchOperationTestInterface deleteMatchOperation;

    @Inject
    UpsertMatchmakerOperation insertMatchmakerOperation;

    @Inject
    UpsertMatchmakerMatchOperation upsertMatchmakerMatchOperation;

    @Inject
    MatchmakerModelFactory matchmakerModelFactory;

    @Inject
    MatchmakerMatchModelFactory matchmakerMatchModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenMatch_whenDeleteMatch_thenDeleted() {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), stageId());
        insertMatchmakerOperation.upsertMatchmaker(TIMEOUT, pgPool, shard, matchmaker);

        final var modeConfig = VersionModeModel.create(modeName(), 4, 8, new ArrayList<>() {{
            add(VersionGroupModel.create("red", 1, 4));
            add(VersionGroupModel.create("blue", 1, 4));
        }});
        final var matchConfig = new MatchmakerMatchConfigModel(modeConfig);
        final var match = matchmakerMatchModelFactory.create(matchmaker.getId(), matchConfig);
        upsertMatchmakerMatchOperation.upsertMatchmakerMatch(TIMEOUT, pgPool, shard, match);

        final var changeContext = deleteMatchOperation.deleteMatchmakerMatch(shard, matchmaker.getId(), match.getId());
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.MATCHMAKER_MATCH_DELETED));
    }

    @Test
    void givenUnknownIds_whenDeleteMatch_thenFalse() {
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

    Long stageId() {
        return generateIdOperation.generateId();
    }

    Long versionId() {
        return generateIdOperation.generateId();
    }

    String modeName() {
        return "mode-" + UUID.randomUUID();
    }
}