package com.omgservers.service.module.runtime.operation;

import com.omgservers.schema.event.EventQualifierEnum;
import com.omgservers.schema.model.runtime.RuntimeConfigModel;
import com.omgservers.schema.model.runtime.RuntimeQualifierEnum;
import com.omgservers.service.factory.runtime.RuntimeModelFactory;
import com.omgservers.service.module.runtime.operation.testInterface.UpsertRuntimeOperationTestInterface;
import com.omgservers.service.server.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class HandleRuntimeCommandByIdsOperationTest extends Assertions {

    @Inject
    UpsertRuntimeOperationTestInterface upsertRuntimeOperation;

    @Inject
    RuntimeModelFactory runtimeModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenRuntime_whenUpsertRuntime_thenInserted() {
        final var shard = 0;
        final var runtime = runtimeModelFactory.create(tenantId(),
                versionId(),
                RuntimeQualifierEnum.MATCH,
                new RuntimeConfigModel());
        final var changeContext = upsertRuntimeOperation.upsertRuntime(shard, runtime);
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.RUNTIME_CREATED));
    }

    @Test
    void givenRuntime_whenUpsertRuntime_thenSkip() {
        final var shard = 0;
        final var runtime = runtimeModelFactory.create(tenantId(),
                versionId(),
                RuntimeQualifierEnum.MATCH,
                new RuntimeConfigModel());
        upsertRuntimeOperation.upsertRuntime(shard, runtime);

        final var changeContext = upsertRuntimeOperation.upsertRuntime(shard, runtime);
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