package com.omgservers.module.matchmaker.impl.operation.deleteMatch;

import com.omgservers.model.match.MatchConfigModel;
import com.omgservers.model.version.VersionGroupModel;
import com.omgservers.model.version.VersionModeModel;
import com.omgservers.module.matchmaker.factory.MatchModelFactory;
import com.omgservers.module.matchmaker.factory.MatchmakerModelFactory;
import com.omgservers.module.matchmaker.impl.operation.upsertMatch.UpsertMatchOperation;
import com.omgservers.module.matchmaker.impl.operation.upsertMatchmaker.UpsertMatchmakerOperation;
import com.omgservers.operation.generateId.GenerateIdOperation;
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
class DeleteMatchOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    DeleteMatchOperation deleteMatchOperation;

    @Inject
    UpsertMatchmakerOperation insertMatchmakerOperation;

    @Inject
    UpsertMatchOperation upsertMatchOperation;

    @Inject
    MatchmakerModelFactory matchmakerModelFactory;

    @Inject
    MatchModelFactory matchModelFactory;

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
        final var matchConfig = MatchConfigModel.create(tenantId(),
                stageId(),
                versionId(),
                modeConfig);
        final var match = matchModelFactory.create(matchmaker.getId(), matchConfig);
        upsertMatchOperation.upsertMatch(TIMEOUT, pgPool, shard, match);

        assertTrue(deleteMatchOperation.deleteMatch(TIMEOUT, pgPool, shard, match.getId()));
    }

    @Test
    void givenUnknownUuid_whenDeleteMatch_thenSkip() {
        final var shard = 0;
        final var id = generateIdOperation.generateId();

        assertFalse(deleteMatchOperation.deleteMatch(TIMEOUT, pgPool, shard, id));
    }

    Long runtimeId() {
        return generateIdOperation.generateId();
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