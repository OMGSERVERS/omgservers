package com.omgservers.application.module.versionModule.impl.operation.upsertVersionOperation;

import com.omgservers.model.version.VersionBytecodeModel;
import com.omgservers.application.factory.VersionModelFactory;
import com.omgservers.model.version.VersionSourceCodeModel;
import com.omgservers.model.version.VersionStageConfigModel;
import com.omgservers.base.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

@Slf4j
@QuarkusTest
class UpsertVersionOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    UpsertVersionOperation upsertVersionOperation;

    @Inject
    VersionModelFactory versionModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void whenUpsertVersion_thenInserted() {
        final var shard = 0;
        final var version = versionModelFactory.create(tenantId(), stageId(), VersionStageConfigModel.create(), VersionSourceCodeModel.create(), VersionBytecodeModel.create());
        assertTrue(upsertVersionOperation.upsertVersion(TIMEOUT, pgPool, shard, version));
    }

    @Test
    void givenVersion_whenUpsertVersion_thenUpdated() {
        final var shard = 0;
        final var version = versionModelFactory.create(tenantId(), stageId(), VersionStageConfigModel.create(), VersionSourceCodeModel.create(), VersionBytecodeModel.create());
        upsertVersionOperation.upsertVersion(TIMEOUT, pgPool, shard, version);

        assertFalse(upsertVersionOperation.upsertVersion(TIMEOUT, pgPool, shard, version));
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long stageId() {
        return generateIdOperation.generateId();
    }
}