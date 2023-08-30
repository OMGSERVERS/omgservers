package com.omgservers.module.version.impl.operation.selectVersion;

import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.version.VersionBytecodeModel;
import com.omgservers.model.version.VersionSourceCodeModel;
import com.omgservers.model.version.VersionStageConfigModel;
import com.omgservers.factory.VersionModelFactory;
import com.omgservers.module.version.impl.operation.upsertVersion.UpsertVersionOperation;
import com.omgservers.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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