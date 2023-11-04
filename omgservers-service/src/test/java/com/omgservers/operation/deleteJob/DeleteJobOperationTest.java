package com.omgservers.operation.deleteJob;

import com.omgservers.model.job.JobQualifierEnum;
import com.omgservers.factory.JobModelFactory;
import com.omgservers.module.system.impl.operation.deleteJob.DeleteJobOperation;
import com.omgservers.module.system.impl.operation.upsertJob.UpsertJobOperation;
import com.omgservers.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class DeleteJobOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    DeleteJobOperation deleteJobOperation;

    @Inject
    UpsertJobOperation upsertJobOperation;

    @Inject
    JobModelFactory jobModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenJob_whenDeleteJob_thenDeleted() {
        final var job = jobModelFactory.create(shardKey(), entityId(), JobQualifierEnum.TENANT);
        upsertJobOperation.upsertJob(TIMEOUT, pgPool, job);

        assertTrue(deleteJobOperation.deleteJob(TIMEOUT, pgPool, job.getShardKey(), job.getEntityId(),
                JobQualifierEnum.TENANT));
    }

    @Test
    void givenUnknownUuids_whenDeleteJob_thenSkip() {
        final var shardKey = generateIdOperation.generateId();
        final var entityId = generateIdOperation.generateId();

        assertFalse(deleteJobOperation.deleteJob(TIMEOUT, pgPool, shardKey, entityId, JobQualifierEnum.TENANT));
    }

    Long shardKey() {
        return generateIdOperation.generateId();
    }

    Long entityId() {
        return generateIdOperation.generateId();
    }
}