package com.omgservers.service.module.runtime.operation;

import com.omgservers.model.runtime.RuntimeConfigModel;
import com.omgservers.model.runtime.RuntimeQualifierEnum;
import com.omgservers.model.runtimePermission.RuntimePermissionEnum;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.factory.runtime.RuntimeModelFactory;
import com.omgservers.service.factory.runtime.RuntimePermissionModelFactory;
import com.omgservers.service.module.runtime.operation.testInterface.UpsertRuntimeOperationTestInterface;
import com.omgservers.service.module.runtime.operation.testInterface.UpsertRuntimePermissionOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertRuntimePermissionOperationTest extends Assertions {

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
    void givenRuntimePermission_whenUpsertRuntimePermission_thenTrue() {
        final var shard = 0;
        final var runtime = runtimeModelFactory.create(tenantId(),
                versionId(),
                RuntimeQualifierEnum.MATCH,
                new RuntimeConfigModel());
        upsertRuntimeOperation.upsertRuntime(shard, runtime);

        final var runtimePermission = runtimePermissionModelFactory.create(runtime.getId(),
                userId(),
                RuntimePermissionEnum.HANDLE_RUNTIME);

        final var changeContext = upsertRuntimePermissionOperation.upsertRuntimePermission(shard, runtimePermission);
        assertTrue(changeContext.getResult());
    }

    @Test
    void givenRuntimePermission_whenUpsertRuntimePermission_thenFalse() {
        final var shard = 0;
        final var runtime = runtimeModelFactory.create(tenantId(),
                versionId(),
                RuntimeQualifierEnum.MATCH,
                new RuntimeConfigModel());
        upsertRuntimeOperation.upsertRuntime(shard, runtime);

        final var runtimePermission = runtimePermissionModelFactory.create(runtime.getId(),
                userId(),
                RuntimePermissionEnum.HANDLE_RUNTIME);
        upsertRuntimePermissionOperation.upsertRuntimePermission(shard, runtimePermission);

        final var changeContext = upsertRuntimePermissionOperation.upsertRuntimePermission(shard, runtimePermission);
        assertFalse(changeContext.getResult());
    }

    @Test
    void givenUnknownId_whenUpsertRuntimePermission_thenException() {
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