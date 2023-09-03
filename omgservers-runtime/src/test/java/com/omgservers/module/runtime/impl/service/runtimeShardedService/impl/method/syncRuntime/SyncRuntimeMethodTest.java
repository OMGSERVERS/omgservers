package com.omgservers.module.runtime.impl.service.runtimeShardedService.impl.method.syncRuntime;

import com.omgservers.dto.runtime.SyncRuntimeShardedRequest;
import com.omgservers.model.runtime.RuntimeConfigModel;
import com.omgservers.model.runtime.RuntimeTypeEnum;
import com.omgservers.module.runtime.factory.RuntimeModelFactory;
import com.omgservers.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class SyncRuntimeMethodTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    SyncRuntimeMethod syncRuntimeMethod;

    @Inject
    RuntimeModelFactory runtimeModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenRuntime_whenSyncRuntime_thenCreated() {
        final var runtime = runtimeModelFactory.create(tenantId(), stageId(), versionId(), matchmakerId(), matchId(), RuntimeTypeEnum.EMBEDDED_LUA, RuntimeConfigModel.create());
        final var syncRuntimeShardedRequest = new SyncRuntimeShardedRequest(runtime);
        final var response = syncRuntimeMethod.syncRuntime(TIMEOUT, syncRuntimeShardedRequest);
        log.info("Method response, response={}", response);
        assertTrue(response.getCreated());
//        assertNotNull(response.getExtendedResponse().getChangeExtendedResponse().getChangeLog());
//        assertNotNull(response.getExtendedResponse().getChangeExtendedResponse().getInsertedEvent());
//        assertEquals(EventQualifierEnum.RUNTIME_CREATED, response.getExtendedResponse().getChangeExtendedResponse().getInsertedEvent().getQualifier());
    }

    @Test
    void givenRuntime_whenSyncRuntimeAgain_thenUpdated() {
        final var runtime = runtimeModelFactory.create(tenantId(), stageId(), versionId(), matchmakerId(), matchId(), RuntimeTypeEnum.EMBEDDED_LUA, RuntimeConfigModel.create());
        final var syncRuntimeShardedRequest = new SyncRuntimeShardedRequest(runtime);
        syncRuntimeMethod.syncRuntime(TIMEOUT, syncRuntimeShardedRequest);
        final var response = syncRuntimeMethod.syncRuntime(TIMEOUT, syncRuntimeShardedRequest);
        log.info("Method response, response={}", response);
        assertFalse(response.getCreated());
//        assertNotNull(response.getExtendedResponse().getChangeExtendedResponse().getChangeLog());
//        assertNotNull(response.getExtendedResponse().getChangeExtendedResponse().getInsertedEvent());
//        assertEquals(EventQualifierEnum.RUNTIME_UPDATED, response.getExtendedResponse().getChangeExtendedResponse().getInsertedEvent().getQualifier());
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long stageId() {
        return generateIdOperation.generateId();
    }

    Long versionId() {
        return generateIdOperation.generateId();
    }

    Long matchmakerId() {
        return generateIdOperation.generateId();
    }

    Long matchId() {
        return generateIdOperation.generateId();
    }
}