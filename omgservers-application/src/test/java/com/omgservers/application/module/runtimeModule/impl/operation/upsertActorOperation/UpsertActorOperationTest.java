package com.omgservers.application.module.runtimeModule.impl.operation.upsertActorOperation;

import com.omgservers.application.exception.ServerSideNotFoundException;
import com.omgservers.application.module.runtimeModule.impl.operation.upsertRuntimeOperation.UpsertRuntimeOperation;
import com.omgservers.application.module.runtimeModule.model.actor.ActorConfigModel;
import com.omgservers.application.module.runtimeModule.model.actor.ActorModelFactory;
import com.omgservers.application.module.runtimeModule.model.runtime.RuntimeConfigModel;
import com.omgservers.application.module.runtimeModule.model.runtime.RuntimeModelFactory;
import com.omgservers.application.module.runtimeModule.model.runtime.RuntimeTypeEnum;
import com.omgservers.application.operation.generateIdOperation.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertActorOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    UpsertRuntimeOperation upsertRuntimeOperation;

    @Inject
    UpsertActorOperation upsertActorOperation;

    @Inject
    RuntimeModelFactory runtimeModelFactory;

    @Inject
    ActorModelFactory actorModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenActor_whenUpsertActor_thenInserted() {
        final var shard = 0;
        final var runtime = runtimeModelFactory.create(matchmakerId(), matchId(), RuntimeConfigModel.create(RuntimeTypeEnum.EMBEDDED_LUA));
        upsertRuntimeOperation.upsertRuntime(TIMEOUT, pgPool, shard, runtime);

        final var actor = actorModelFactory.create(runtime.getId(), userId(), clientId(), ActorConfigModel.create());
        assertTrue(upsertActorOperation.upsertActor(TIMEOUT, pgPool, shard, actor));
    }

    @Test
    void givenActor_whenInsertActorAgain_thenUpdated() {
        final var shard = 0;
        final var runtime = runtimeModelFactory.create(matchmakerId(), matchId(), RuntimeConfigModel.create(RuntimeTypeEnum.EMBEDDED_LUA));
        upsertRuntimeOperation.upsertRuntime(TIMEOUT, pgPool, shard, runtime);

        final var actor = actorModelFactory.create(runtime.getId(), userId(), clientId(), ActorConfigModel.create());
        upsertActorOperation.upsertActor(TIMEOUT, pgPool, shard, actor);

        assertFalse(upsertActorOperation.upsertActor(TIMEOUT, pgPool, shard, actor));
    }

    @Test
    void givenUnknownRuntimeUuid_whenUpsertActor_thenServerSideNotFoundException() {
        final var shard = 0;
        final var actor = actorModelFactory.create(runtimeId(), userId(), clientId(), ActorConfigModel.create());
        final var exception = assertThrows(ServerSideNotFoundException.class, () -> upsertActorOperation
                .upsertActor(TIMEOUT, pgPool, shard, actor));
        log.info("Exception: {}", exception.getMessage());
    }

    Long runtimeId() {
        return generateIdOperation.generateId();
    }

    Long matchmakerId() {
        return generateIdOperation.generateId();
    }

    Long matchId() {
        return generateIdOperation.generateId();
    }

    Long userId() {
        return generateIdOperation.generateId();
    }

    Long clientId() {
        return generateIdOperation.generateId();
    }
}