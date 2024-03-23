package com.omgservers.service.module.runtime.operation;

import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.runtime.RuntimeConfigModel;
import com.omgservers.model.runtime.RuntimeQualifierEnum;
import com.omgservers.service.factory.RuntimeModelFactory;
import com.omgservers.service.module.runtime.operation.testInterface.DeleteRuntimeOperationTestInterface;
import com.omgservers.service.module.runtime.operation.testInterface.UpsertRuntimeOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class DeleteRuntimeOperationTest extends Assertions {

    @Inject
    DeleteRuntimeOperationTestInterface deleteRuntimeOperation;

    @Inject
    UpsertRuntimeOperationTestInterface upsertRuntimeOperation;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    RuntimeModelFactory runtimeModelFactory;

    @Test
    void givenRuntime_whenRuntimeTenant_thenDeleted() {
        final var shard = 0;
        final var runtime1 = runtimeModelFactory.create(tenantId(), versionId(), RuntimeQualifierEnum.MATCH,
                new RuntimeConfigModel());
        upsertRuntimeOperation.upsertRuntime(shard, runtime1);

        final var changeContext = deleteRuntimeOperation.deleteRuntime(shard, runtime1.getId());
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.RUNTIME_DELETED));
    }

    @Test
    void givenUnknownUuid_whenDeleteTenant_thenSkip() {
        final var shard = 0;
        final var id = generateIdOperation.generateId();

        final var changeContext = deleteRuntimeOperation.deleteRuntime(shard, id);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.RUNTIME_DELETED));
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long versionId() {
        return generateIdOperation.generateId();
    }
}