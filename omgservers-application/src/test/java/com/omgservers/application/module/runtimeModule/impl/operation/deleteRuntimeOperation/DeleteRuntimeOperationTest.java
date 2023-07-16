package com.omgservers.application.module.runtimeModule.impl.operation.deleteRuntimeOperation;

import com.omgservers.application.module.runtimeModule.impl.operation.insertRuntimeOperation.InsertRuntimeOperation;
import com.omgservers.application.module.runtimeModule.model.RuntimeConfigModel;
import com.omgservers.application.module.runtimeModule.model.RuntimeModel;
import com.omgservers.application.module.runtimeModule.model.RuntimeTypeEnum;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@Slf4j
@QuarkusTest
class DeleteRuntimeOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    DeleteRuntimeOperation deleteRuntimeOperation;

    @Inject
    InsertRuntimeOperation insertRuntimeOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenRuntime_whenRuntimeTenant_thenDeleted() {
        final var shard = 0;
        final var runtime1 = RuntimeModel.create(matchmakerUuid(), matchUuid(), RuntimeConfigModel.create(RuntimeTypeEnum.EMBEDDED_LUA));
        insertRuntimeOperation.insertRuntime(TIMEOUT, pgPool, shard, runtime1);

        assertTrue(deleteRuntimeOperation.deleteRuntime(TIMEOUT, pgPool, shard, runtime1.getUuid()));
    }

    @Test
    void givenUnknownUuid_whenDeleteTenant_thenSkip() {
        final var shard = 0;
        final var uuid = UUID.randomUUID();

        assertFalse(deleteRuntimeOperation.deleteRuntime(TIMEOUT, pgPool, shard, uuid));
    }

    UUID matchmakerUuid() {
        return UUID.randomUUID();
    }

    UUID matchUuid() {
        return UUID.randomUUID();
    }
}