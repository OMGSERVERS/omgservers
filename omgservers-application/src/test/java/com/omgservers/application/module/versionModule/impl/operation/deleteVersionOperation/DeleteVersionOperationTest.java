package com.omgservers.application.module.versionModule.impl.operation.deleteVersionOperation;

import com.omgservers.application.module.versionModule.impl.operation.upsertVersionOperation.UpsertVersionOperation;
import com.omgservers.model.version.VersionBytecodeModel;
import com.omgservers.application.factory.VersionModelFactory;
import com.omgservers.model.version.VersionSourceCodeModel;
import com.omgservers.model.version.VersionStageConfigModel;
import com.omgservers.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

@Slf4j
@QuarkusTest
class DeleteVersionOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    DeleteVersionOperation deleteVersionOperation;

    @Inject
    UpsertVersionOperation upsertVersionOperation;

    @Inject
    VersionModelFactory versionModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenVersion_whenDeleteVersion_thenDeleted() {
        final var shard = 0;
        final var version = versionModelFactory.create(tenantId(), stageId(), VersionStageConfigModel.create(), VersionSourceCodeModel.create(), VersionBytecodeModel.create());
        final var id = version.getId();
        upsertVersionOperation.upsertVersion(TIMEOUT, pgPool, shard, version);

        assertTrue(deleteVersionOperation.deleteVersion(TIMEOUT, pgPool, shard, id));
    }

    @Test
    void givenUnknownUuid_whenDeleteVersion_thenSkip() {
        final var shard = 0;
        final var id = generateIdOperation.generateId();

        assertFalse(deleteVersionOperation.deleteVersion(TIMEOUT, pgPool, shard, id));
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long stageId() {
        return generateIdOperation.generateId();
    }
}