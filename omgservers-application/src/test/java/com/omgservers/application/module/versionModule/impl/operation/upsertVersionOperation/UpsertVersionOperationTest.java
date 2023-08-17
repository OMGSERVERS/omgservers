package com.omgservers.application.module.versionModule.impl.operation.upsertVersionOperation;

import com.omgservers.application.module.versionModule.model.VersionBytecodeModel;
import com.omgservers.application.module.versionModule.model.VersionModel;
import com.omgservers.application.module.versionModule.model.VersionModelFactory;
import com.omgservers.application.module.versionModule.model.VersionSourceCodeModel;
import com.omgservers.application.module.versionModule.model.VersionStageConfigModel;
import com.omgservers.application.operation.generateIdOperation.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

import java.util.UUID;

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