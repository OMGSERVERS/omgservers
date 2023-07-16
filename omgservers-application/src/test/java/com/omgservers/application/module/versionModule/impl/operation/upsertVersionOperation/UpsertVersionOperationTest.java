package com.omgservers.application.module.versionModule.impl.operation.upsertVersionOperation;

import com.omgservers.application.module.versionModule.model.VersionModel;
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
    PgPool pgPool;

    @Test
    void whenUpsertVersion_thenInserted() {
        final var shard = 0;
        final var version = VersionModel.create(tenantUuid(), stageUuid());
        assertTrue(upsertVersionOperation.upsertVersion(TIMEOUT, pgPool, shard, version).getItem2());
    }

    @Test
    void givenVersion_whenUpsertVersion_thenUpdated() {
        final var shard = 0;
        final var version = VersionModel.create(tenantUuid(), stageUuid());
        upsertVersionOperation.upsertVersion(TIMEOUT, pgPool, shard, version);

        assertFalse(upsertVersionOperation.upsertVersion(TIMEOUT, pgPool, shard, version).getItem2());
    }

    UUID tenantUuid() {
        return UUID.randomUUID();
    }

    UUID stageUuid() {
        return UUID.randomUUID();
    }
}