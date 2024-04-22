package com.omgservers.service.module.runtime.operation;

import com.omgservers.model.runtime.RuntimeConfigModel;
import com.omgservers.model.runtime.RuntimeQualifierEnum;
import com.omgservers.model.runtimeCommand.body.InitRuntimeCommandBodyModel;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.runtime.RuntimeCommandModelFactory;
import com.omgservers.service.factory.runtime.RuntimeModelFactory;
import com.omgservers.service.module.runtime.operation.testInterface.UpsertRuntimeCommandOperationTestInterface;
import com.omgservers.service.module.runtime.operation.testInterface.UpsertRuntimeOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertRuntimeCommandOperationTest extends Assertions {

    @Inject
    UpsertRuntimeOperationTestInterface upsertRuntimeOperation;

    @Inject
    UpsertRuntimeCommandOperationTestInterface upsertRuntimeCommandOperation;

    @Inject
    RuntimeModelFactory runtimeModelFactory;

    @Inject
    RuntimeCommandModelFactory runtimeCommandModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenRuntimeCommand_whenUpsertRuntimeCommand_thenInserted() {
        final var shard = 0;
        final var runtime = runtimeModelFactory.create(tenantId(),
                versionId(),
                RuntimeQualifierEnum.MATCH,
                new RuntimeConfigModel());
        upsertRuntimeOperation.upsertRuntime(shard, runtime);

        final var runtimeCommand = runtimeCommandModelFactory.create(runtime.getId(),
                new InitRuntimeCommandBodyModel());
        final var changeContext = upsertRuntimeCommandOperation.upsertRuntimeCommand(shard, runtimeCommand);
        assertTrue(changeContext.getResult());
    }

    @Test
    void givenRuntimeCommand_whenUpsertRuntimeCommand_thenUpdated() {
        final var shard = 0;
        final var runtime = runtimeModelFactory.create(tenantId(),
                versionId(),
                RuntimeQualifierEnum.MATCH,
                new RuntimeConfigModel());
        upsertRuntimeOperation.upsertRuntime(shard, runtime);

        final var runtimeCommand = runtimeCommandModelFactory.create(runtime.getId(),
                new InitRuntimeCommandBodyModel());
        upsertRuntimeCommandOperation.upsertRuntimeCommand(shard, runtimeCommand);

        final var changeContext = upsertRuntimeCommandOperation.upsertRuntimeCommand(shard, runtimeCommand);
        assertFalse(changeContext.getResult());
    }

    @Test
    void givenUnknownId_whenUpsertRuntimeCommand_thenException() {
        final var shard = 0;
        final var runtimeCommand = runtimeCommandModelFactory.create(runtimeId(), new InitRuntimeCommandBodyModel());
        assertThrows(ServerSideBadRequestException.class, () -> upsertRuntimeCommandOperation
                .upsertRuntimeCommand(shard, runtimeCommand));
    }

    @Test
    void givenRuntimeCommand_whenUpsertRuntimeCommand_thenIdempotencyViolation() {
        final var shard = 0;
        final var runtime = runtimeModelFactory.create(tenantId(),
                versionId(),
                RuntimeQualifierEnum.MATCH,
                new RuntimeConfigModel());
        upsertRuntimeOperation.upsertRuntime(shard, runtime);

        final var runtimeCommand1 = runtimeCommandModelFactory.create(runtime.getId(),
                new InitRuntimeCommandBodyModel());
        upsertRuntimeCommandOperation.upsertRuntimeCommand(shard, runtimeCommand1);

        final var runtimeCommand2 = runtimeCommandModelFactory.create(runtime.getId(),
                new InitRuntimeCommandBodyModel(),
                runtimeCommand1.getIdempotencyKey());

        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertRuntimeCommandOperation.upsertRuntimeCommand(shard, runtimeCommand2));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED, exception.getQualifier());
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long versionId() {
        return generateIdOperation.generateId();
    }

    Long runtimeId() {
        return generateIdOperation.generateId();
    }
}