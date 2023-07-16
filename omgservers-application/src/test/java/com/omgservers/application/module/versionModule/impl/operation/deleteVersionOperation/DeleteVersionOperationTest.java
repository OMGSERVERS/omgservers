package com.omgservers.application.module.versionModule.impl.operation.deleteVersionOperation;

import com.omgservers.application.module.versionModule.impl.operation.upsertVersionOperation.UpsertVersionOperation;
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
class DeleteVersionOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    DeleteVersionOperation deleteVersionOperation;

    @Inject
    UpsertVersionOperation upsertVersionOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenVersion_whenDeleteVersion_thenDeleted() {
        final var shard = 0;
        final var version = VersionModel.create(tenantUuid(), stageUuid());
        final var uuid = version.getUuid();
        upsertVersionOperation.upsertVersion(TIMEOUT, pgPool, shard, version);

        assertTrue(deleteVersionOperation.deleteVersion(TIMEOUT, pgPool, shard, uuid));
    }

    @Test
    void givenUnknownUuid_whenDeleteVersion_thenSkip() {
        final var shard = 0;
        final var uuid = UUID.randomUUID();

        assertFalse(deleteVersionOperation.deleteVersion(TIMEOUT, pgPool, shard, uuid));
    }

    UUID tenantUuid() {
        return UUID.randomUUID();
    }

    UUID stageUuid() {
        return UUID.randomUUID();
    }
}