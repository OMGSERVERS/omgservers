package com.omgservers.module.runtime.impl.operation.handleRuntimeCommand;

import com.omgservers.dto.context.HandleInitRuntimeCommandResponse;
import com.omgservers.factory.RuntimeCommandModelFactory;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
import com.omgservers.model.runtimeCommand.body.InitRuntimeCommandBodyModel;
import com.omgservers.module.context.impl.service.handlerService.ContextService;
import com.omgservers.operation.generateId.GenerateIdOperation;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
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
    RuntimeCommandModelFactory runtimeCommandModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @InjectMock
    ContextService contextService;

    @Test
    void givenRuntimeCommand_whenHandleRuntimeCommandAndContextServiceReturnsTrue_thenTrue() {
        final var runtimeCommand = runtimeCommandModelFactory.create(runtimeId(), new InitRuntimeCommandBodyModel());

        Mockito.when(contextService.handleInitRuntimeCommand(any())).thenReturn(Uni.createFrom().item(new HandleInitRuntimeCommandResponse(true)));

        final var result = handleRuntimeCommandOperation.handleRuntimeCommand(TIMEOUT, runtimeCommand);
        assertTrue(result);
    }

    @Test
    void givenRuntimeCommand_whenHandleRuntimeCommandAndContextServiceReturnsFalse_thenFalse() {
        final var runtimeCommand = runtimeCommandModelFactory.create(runtimeId(), new InitRuntimeCommandBodyModel());

        Mockito.when(contextService.handleInitRuntimeCommand(any())).thenReturn(Uni.createFrom().item(new HandleInitRuntimeCommandResponse(false)));

        final var result = handleRuntimeCommandOperation.handleRuntimeCommand(TIMEOUT, runtimeCommand);
        assertFalse(result);
    }

    @Test
    void givenRuntimeCommand_whenHandleRuntimeCommandAndContextServiceReturnFailure_thenFalse() {
        final var runtimeCommand = runtimeCommandModelFactory.create(runtimeId(), new InitRuntimeCommandBodyModel());

        Mockito.when(contextService.handleInitRuntimeCommand(any())).thenReturn(Uni.createFrom().failure(new RuntimeException("test exception")));

        final var result = handleRuntimeCommandOperation.handleRuntimeCommand(TIMEOUT, runtimeCommand);
        assertFalse(result);
    }

    @Test
    void givenRuntimeCommandWithWrongBodyType_whenHandleRuntimeCommand_thenFalse() {
        final var runtimeCommand = runtimeCommandModelFactory.create(runtimeId(), new InitRuntimeCommandBodyModel());
        // Misconfiguration
        runtimeCommand.setQualifier(RuntimeCommandQualifierEnum.STOP_RUNTIME);
        final var result = handleRuntimeCommandOperation.handleRuntimeCommand(TIMEOUT, runtimeCommand);
        assertFalse(result);
    }

    Long runtimeId() {
        return generateIdOperation.generateId();
    }
}