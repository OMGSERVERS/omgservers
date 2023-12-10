package com.omgservers.service.operation;

import com.omgservers.model.job.JobQualifierEnum;
import com.omgservers.service.factory.JobModelFactory;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import com.omgservers.service.operation.testInterface.DeleteJobOperationTestInterface;
import com.omgservers.service.operation.testInterface.UpsertJobOperationTestInterface;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class DeleteEntityOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    DeleteJobOperationTestInterface deleteJobOperation;

    @Inject
    UpsertJobOperationTestInterface upsertJobOperation;

    @Inject
    JobModelFactory jobModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenJob_whenDeleteJob_thenTrue() {
        final var job = jobModelFactory.create(shardKey(),
                entityId(),
                JobQualifierEnum.TENANT);
        upsertJobOperation.upsertJob(job);

        final var changeContext = deleteJobOperation.deleteJob(job.getId());
        assertTrue(changeContext.getResult());
    }

    @Test
    void givenUnknownId_whenDeleteJob_thenFalse() {
        final var unknownId = generateIdOperation.generateId();
        final var changeContext = deleteJobOperation.deleteJob(unknownId);
        assertFalse(changeContext.getResult());
    }

    Long shardKey() {
        return generateIdOperation.generateId();
    }

    Long entityId() {
        return generateIdOperation.generateId();
    }
}