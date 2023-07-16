package com.omgservers.application.module.versionModule.impl.operation.selectVersionOperation;

import com.omgservers.application.module.versionModule.impl.operation.upsertVersionOperation.UpsertVersionOperation;
import com.omgservers.application.module.versionModule.model.VersionModel;
import com.omgservers.application.exception.ServerSideNotFoundException;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

import java.util.UUID;

@Slf4j
@QuarkusTest
class SelectVersionOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    SelectVersionOperation selectVersionOperation;

    @Inject
    UpsertVersionOperation upsertVersionOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenVersion_whenSelectVersion_thenSelected() {
        final var shard = 0;
        final var version1 = VersionModel.create(tenantUuid(), stageUuid());
        final var versionUuid = version1.getUuid();
        upsertVersionOperation.upsertVersion(TIMEOUT, pgPool, shard, version1);

        final var version2 = selectVersionOperation.selectVersion(TIMEOUT, pgPool, shard, versionUuid);
        assertEquals(version1, version2);
    }

    @Test
    void givenUnknownUuid_whenSelectVersion_thenServerSideNotFoundException() {
        final var shard = 0;
        final var uuid = UUID.randomUUID();

        assertThrows(ServerSideNotFoundException.class, () -> selectVersionOperation
                .selectVersion(TIMEOUT, pgPool, shard, uuid));
    }

    UUID tenantUuid() {
        return UUID.randomUUID();
    }

    UUID stageUuid() {
        return UUID.randomUUID();
    }
}