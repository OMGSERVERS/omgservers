package com.omgservers.service.shard.runtime.operation;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.runtime.RuntimeConfigDto;
import com.omgservers.schema.model.runtime.RuntimeQualifierEnum;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.factory.runtime.RuntimeModelFactory;
import com.omgservers.service.shard.runtime.operation.testInterface.UpsertRuntimeOperationTestInterface;
import com.omgservers.service.operation.server.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class HandleRuntimeCommandByIdsOperationTest extends BaseTestClass {

    @Inject
    UpsertRuntimeOperationTestInterface upsertRuntimeOperation;

    @Inject
    RuntimeModelFactory runtimeModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenRuntime_whenUpsertRuntime_thenInserted() {
        final var slot = 0;
        final var runtime = runtimeModelFactory.create(tenantId(),
                versionId(),
                RuntimeQualifierEnum.MATCH,
                new RuntimeConfigDto());
        final var changeContext = upsertRuntimeOperation.upsertRuntime(slot, runtime);
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.RUNTIME_CREATED));
    }

    @Test
    void givenRuntime_whenUpsertRuntime_thenSkip() {
        final var slot = 0;
        final var runtime = runtimeModelFactory.create(tenantId(),
                versionId(),
                RuntimeQualifierEnum.MATCH,
                new RuntimeConfigDto());
        upsertRuntimeOperation.upsertRuntime(slot, runtime);

        final var changeContext = upsertRuntimeOperation.upsertRuntime(slot, runtime);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.RUNTIME_CREATED));
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long versionId() {
        return generateIdOperation.generateId();
    }
}