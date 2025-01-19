package com.omgservers.service.module.runtime.operation;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.runtime.RuntimeConfigDto;
import com.omgservers.schema.model.runtime.RuntimeQualifierEnum;
import com.omgservers.schema.model.runtimePermission.RuntimePermissionEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.factory.runtime.RuntimeModelFactory;
import com.omgservers.service.factory.runtime.RuntimePermissionModelFactory;
import com.omgservers.service.module.runtime.operation.testInterface.UpsertRuntimeOperationTestInterface;
import com.omgservers.service.module.runtime.operation.testInterface.UpsertRuntimePermissionOperationTestInterface;
import com.omgservers.service.operation.server.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertRuntimePermissionOperationTest extends BaseTestClass {

    @Inject
    UpsertRuntimePermissionOperationTestInterface upsertRuntimePermissionOperation;

    @Inject
    UpsertRuntimeOperationTestInterface upsertRuntimeOperation;

    @Inject
    RuntimeModelFactory runtimeModelFactory;

    @Inject
    RuntimePermissionModelFactory runtimePermissionModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenRuntimePermission_whenExecute_thenTrue() {
        final var shard = 0;
        final var runtime = runtimeModelFactory.create(tenantId(),
                versionId(),
                RuntimeQualifierEnum.MATCH,
                new RuntimeConfigDto());
        upsertRuntimeOperation.upsertRuntime(shard, runtime);

        final var runtimePermission = runtimePermissionModelFactory.create(runtime.getId(),
                userId(),
                RuntimePermissionEnum.HANDLE_RUNTIME);

        final var changeContext = upsertRuntimePermissionOperation.upsertRuntimePermission(shard, runtimePermission);
        assertTrue(changeContext.getResult());
    }

    @Test
    void givenRuntimePermission_whenExecute_thenFalse() {
        final var shard = 0;
        final var runtime = runtimeModelFactory.create(tenantId(),
                versionId(),
                RuntimeQualifierEnum.MATCH,
                new RuntimeConfigDto());
        upsertRuntimeOperation.upsertRuntime(shard, runtime);

        final var runtimePermission = runtimePermissionModelFactory.create(runtime.getId(),
                userId(),
                RuntimePermissionEnum.HANDLE_RUNTIME);
        upsertRuntimePermissionOperation.upsertRuntimePermission(shard, runtimePermission);

        final var changeContext = upsertRuntimePermissionOperation.upsertRuntimePermission(shard, runtimePermission);
        assertFalse(changeContext.getResult());
    }

    @Test
    void givenUnknownId_whenExecute_thenException() {
        final var shard = 0;
        final var runtimePermission = runtimePermissionModelFactory.create(runtimeId(),
                userId(),
                RuntimePermissionEnum.HANDLE_RUNTIME);
        final var exception = assertThrows(ServerSideBadRequestException.class, () -> upsertRuntimePermissionOperation
                .upsertRuntimePermission(shard, runtimePermission));
        assertEquals(ExceptionQualifierEnum.DB_CONSTRAINT_VIOLATED, exception.getQualifier());
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long versionId() {
        return generateIdOperation.generateId();
    }

    Long userId() {
        return generateIdOperation.generateId();
    }

    Long runtimeId() {
        return generateIdOperation.generateId();
    }
}