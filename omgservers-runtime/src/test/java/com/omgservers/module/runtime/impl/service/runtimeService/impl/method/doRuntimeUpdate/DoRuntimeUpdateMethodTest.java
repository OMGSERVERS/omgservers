package com.omgservers.module.runtime.impl.service.runtimeService.impl.method.doRuntimeUpdate;

import com.omgservers.module.runtime.factory.RuntimeCommandModelFactory;
import com.omgservers.module.runtime.factory.RuntimeModelFactory;
import com.omgservers.module.runtime.impl.operation.handleRuntimeCommand.HandleRuntimeCommandOperation;
import com.omgservers.module.runtime.impl.service.runtimeService.impl.method.syncRuntime.SyncRuntimeMethod;
import com.omgservers.module.runtime.impl.service.runtimeService.impl.method.syncRuntimeCommand.SyncRuntimeCommandMethod;
import com.omgservers.operation.generateId.GenerateIdOperation;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class DoRuntimeUpdateMethodTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    DoRuntimeUpdateMethod doRuntimeUpdateMethod;

    @Inject
    SyncRuntimeMethod syncRuntimeMethod;

    @Inject
    SyncRuntimeCommandMethod syncRuntimeCommandMethod;

    @Inject
    RuntimeModelFactory runtimeModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    RuntimeCommandModelFactory runtimeCommandModelFactory;

    @InjectMock
    HandleRuntimeCommandOperation handleRuntimeCommandOperation;

    @Test
    void doRuntimeUpdateTest() {
//        final var runtime = runtimeModelFactory.create(tenantId(), stageId(), versionId(), matchmakerId(), matchId(), RuntimeTypeEnum.EMBEDDED_LUA, RuntimeConfigModel.create());
//        final var syncRuntimeShardedRequest = new SyncRuntimeRequest(runtime);
//        syncRuntimeMethod.syncRuntime(TIMEOUT, syncRuntimeShardedRequest);
//
//        final var runtimeCommand1 = runtimeCommandModelFactory.create(runtime.getId(), new InitRuntimeCommandBodyModel());
//        final var syncRuntimeCommandShardedRequest1 = new SyncRuntimeCommandRequest(runtimeCommand1);
//        syncRuntimeCommandMethod.syncRuntimeCommand(TIMEOUT, syncRuntimeCommandShardedRequest1);
//
//        Mockito.when(handleRuntimeCommandOperation.handleRuntimeCommand(runtimeCommand1)).thenReturn(Uni.createFrom().item(true));
//
//        final var runtimeCommand2 = runtimeCommandModelFactory.create(runtime.getId(), new StopRuntimeCommandBodyModel());
//        final var syncRuntimeCommandShardedRequest2 = new SyncRuntimeCommandRequest(runtimeCommand2);
//        syncRuntimeCommandMethod.syncRuntimeCommand(TIMEOUT, syncRuntimeCommandShardedRequest2);
//
//        Mockito.when(handleRuntimeCommandOperation.handleRuntimeCommand(runtimeCommand2)).thenReturn(Uni.createFrom().item(false));
//
//        final var doRuntimeUpdateShardedRequest = new DoRuntimeUpdateRequest(runtime.getId());
//        final var response = doRuntimeUpdateMethod.doRuntimeUpdate(TIMEOUT, doRuntimeUpdateShardedRequest);
//        // Update command is added by automatically every step
//        assertEquals(2 + 1, response.getHandledCommands());

//        final var affectedCommand1 = response.getExtendedResponse().getAffectedCommands().get(0);
//        assertEquals(runtimeCommand1.getId(), affectedCommand1.getId());
//        assertEquals(RuntimeCommandStatusEnum.PROCESSED, affectedCommand1.getStatus());
//        assertEquals(1, affectedCommand1.getStep());
//
//        final var affectedCommand2 = response.getExtendedResponse().getAffectedCommands().get(1);
//        assertEquals(runtimeCommand2.getId(), affectedCommand2.getId());
//        assertEquals(RuntimeCommandStatusEnum.FAILED, affectedCommand2.getStatus());
//        assertEquals(1, affectedCommand2.getStep());
//
//        final var affectedCommand3 = response.getExtendedResponse().getAffectedCommands().get(2);
//        assertEquals(RuntimeCommandQualifierEnum.UPDATE_RUNTIME, affectedCommand3.getQualifier());
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