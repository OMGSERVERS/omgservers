package com.omgservers.service.shard.runtime.operation;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.runtime.RuntimeConfigDto;
import com.omgservers.schema.model.runtime.RuntimeQualifierEnum;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.factory.runtime.RuntimeModelFactory;
import com.omgservers.service.shard.runtime.operation.testInterface.DeleteRuntimeOperationTestInterface;
import com.omgservers.service.shard.runtime.operation.testInterface.UpsertRuntimeOperationTestInterface;
import com.omgservers.service.operation.server.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class DeleteRuntimeOperationTest extends BaseTestClass {

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
                new RuntimeConfigDto());
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