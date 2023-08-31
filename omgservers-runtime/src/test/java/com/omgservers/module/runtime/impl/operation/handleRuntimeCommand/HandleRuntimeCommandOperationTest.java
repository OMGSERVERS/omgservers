package com.omgservers.module.runtime.impl.operation.handleRuntimeCommand;

import com.omgservers.dto.context.HandleInitRuntimeCommandResponse;
import com.omgservers.module.runtime.factory.RuntimeCommandModelFactory;
import com.omgservers.module.runtime.factory.RuntimeModelFactory;
import com.omgservers.model.runtime.RuntimeConfigModel;
import com.omgservers.model.runtime.RuntimeTypeEnum;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
import com.omgservers.model.runtimeCommand.body.InitRuntimeCommandBodyModel;
import com.omgservers.module.context.impl.service.contextService.ContextService;
import com.omgservers.module.runtime.impl.operation.upsertRuntime.UpsertRuntimeOperation;
import com.omgservers.operation.generateId.GenerateIdOperation;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;

@Slf4j
@QuarkusTest
class HandleRuntimeCommandOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    HandleRuntimeCommandOperation handleRuntimeCommandOperation;

    @Inject
    UpsertRuntimeOperation upsertRuntimeOperation;

    @Inject
    RuntimeModelFactory runtimeModelFactory;

    @Inject
    RuntimeCommandModelFactory runtimeCommandModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @InjectMock
    ContextService contextService;

    @Test
    void givenRuntimeCommand_whenHandleRuntimeCommandAndContextServiceReturnsTrue_thenTrue() {
        final var shard = 0;
        final var runtime = runtimeModelFactory.create(tenantId(), stageId(), versionId(), matchmakerId(), matchId(), RuntimeTypeEnum.EMBEDDED_LUA, RuntimeConfigModel.create());
        upsertRuntimeOperation.upsertRuntime(TIMEOUT, pgPool, shard, runtime);

        final var runtimeCommand = runtimeCommandModelFactory.create(runtime.getId(), new InitRuntimeCommandBodyModel());

        Mockito.when(contextService.handleInitRuntimeCommand(any())).thenReturn(Uni.createFrom().item(new HandleInitRuntimeCommandResponse(true)));

        final var result = handleRuntimeCommandOperation.handleRuntimeCommand(TIMEOUT, runtimeCommand);
        assertTrue(result);
    }

    @Test
    void givenRuntimeCommand_whenHandleRuntimeCommandAndContextServiceReturnsFalse_thenFalse() {
        final var shard = 0;
        final var runtime = runtimeModelFactory.create(tenantId(), stageId(), versionId(), matchmakerId(), matchId(), RuntimeTypeEnum.EMBEDDED_LUA, RuntimeConfigModel.create());
        upsertRuntimeOperation.upsertRuntime(TIMEOUT, pgPool, shard, runtime);

        final var runtimeCommand = runtimeCommandModelFactory.create(runtime.getId(), new InitRuntimeCommandBodyModel());

        Mockito.when(contextService.handleInitRuntimeCommand(any())).thenReturn(Uni.createFrom().item(new HandleInitRuntimeCommandResponse(false)));

        final var result = handleRuntimeCommandOperation.handleRuntimeCommand(TIMEOUT, runtimeCommand);
        assertFalse(result);
    }

    @Test
    void givenRuntimeCommand_whenHandleRuntimeCommandAndContextServiceReturnFailure_thenFalse() {
        final var shard = 0;
        final var runtime = runtimeModelFactory.create(tenantId(), stageId(), versionId(), matchmakerId(), matchId(), RuntimeTypeEnum.EMBEDDED_LUA, RuntimeConfigModel.create());
        upsertRuntimeOperation.upsertRuntime(TIMEOUT, pgPool, shard, runtime);

        final var runtimeCommand = runtimeCommandModelFactory.create(runtime.getId(), new InitRuntimeCommandBodyModel());

        Mockito.when(contextService.handleInitRuntimeCommand(any())).thenReturn(Uni.createFrom().failure(new RuntimeException("test exception")));

        final var result = handleRuntimeCommandOperation.handleRuntimeCommand(TIMEOUT, runtimeCommand);
        assertFalse(result);
    }

    @Test
    void givenRuntimeCommandWithWrongBodyType_whenHandleRuntimeCommand_thenFalse() {
        final var shard = 0;
        final var runtime = runtimeModelFactory.create(tenantId(), stageId(), versionId(), matchmakerId(), matchId(), RuntimeTypeEnum.EMBEDDED_LUA, RuntimeConfigModel.create());
        upsertRuntimeOperation.upsertRuntime(TIMEOUT, pgPool, shard, runtime);

        final var runtimeCommand = runtimeCommandModelFactory.create(runtime.getId(), new InitRuntimeCommandBodyModel());
        // Misconfiguration
        runtimeCommand.setQualifier(RuntimeCommandQualifierEnum.STOP_RUNTIME);
        final var result = handleRuntimeCommandOperation.handleRuntimeCommand(TIMEOUT, runtimeCommand);
        assertFalse(result);
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