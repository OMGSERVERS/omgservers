package com.omgservers.application.module.versionModule.impl.operation.selectVersionOperation;

import com.omgservers.application.module.versionModule.impl.operation.upsertVersionOperation.UpsertVersionOperation;
import com.omgservers.model.version.VersionBytecodeModel;
import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.base.factory.VersionModelFactory;
import com.omgservers.model.version.VersionSourceCodeModel;
import com.omgservers.model.version.VersionStageConfigModel;
import com.omgservers.base.impl.operation.generateIdOperation.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

@Slf4j
@QuarkusTest
class SelectVersionOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    SelectVersionOperation selectVersionOperation;

    @Inject
    UpsertVersionOperation upsertVersionOperation;

    @Inject
    VersionModelFactory versionModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenVersion_whenSelectVersion_thenSelected() {
        final var shard = 0;
        final var version1 = versionModelFactory.create(tenantId(), stageId(), VersionStageConfigModel.create(), VersionSourceCodeModel.create(), VersionBytecodeModel.create());
        final var versionUuid = version1.getId();
        upsertVersionOperation.upsertVersion(TIMEOUT, pgPool, shard, version1);

        final var version2 = selectVersionOperation.selectVersion(TIMEOUT, pgPool, shard, versionUuid);
        assertEquals(version1, version2);
    }

    @Test
    void givenUnknownUuid_whenSelectVersion_thenServerSideNotFoundException() {
        final var shard = 0;
        final var id = generateIdOperation.generateId();

        assertThrows(ServerSideNotFoundException.class, () -> selectVersionOperation
                .selectVersion(TIMEOUT, pgPool, shard, id));
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long stageId() {
        return generateIdOperation.generateId();
    }
}