package com.omgservers.service.operation;

import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.job.JobQualifierEnum;
import com.omgservers.service.factory.JobModelFactory;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import com.omgservers.service.operation.testInterface.UpsertJobOperationTestInterface;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertEntityOperationTest extends Assertions {

    @Inject
    UpsertJobOperationTestInterface upsertJobOperation;

    @Inject
    JobModelFactory jobModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenJob_whenUpsertJob_thenTrue() {
        final var job = jobModelFactory.create(shardKey(), entityId(), JobQualifierEnum.TENANT);
        final var changeContext = upsertJobOperation.upsertJob(job);
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.JOB_CREATED));
    }

    @Test
    void givenJob_whenUpsertJob_thenFalse() {
        final var job = jobModelFactory.create(shardKey(), entityId(), JobQualifierEnum.TENANT);
        upsertJobOperation.upsertJob(job);

        final var changeContext = upsertJobOperation.upsertJob(job);
        assertFalse(changeContext.getResult());
        assertTrue(changeContext.getChangeEvents().isEmpty());
    }

    Long shardKey() {
        return generateIdOperation.generateId();
    }

    Long entityId() {
        return generateIdOperation.generateId();
    }
}