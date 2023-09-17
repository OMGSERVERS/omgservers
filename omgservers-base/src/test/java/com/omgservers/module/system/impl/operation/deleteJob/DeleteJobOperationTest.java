package com.omgservers.module.system.impl.operation.deleteJob;

import com.omgservers.module.system.impl.operation.upsertJob.UpsertJobOperation;
import com.omgservers.module.system.factory.JobModelFactory;
import com.omgservers.model.job.JobTypeEnum;
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
        final var job = jobModelFactory.create(shardKey(), entityId(), JobTypeEnum.RUNTIME);
        upsertJobOperation.upsertJob(TIMEOUT, pgPool, job);

        assertTrue(deleteJobOperation.deleteJob(TIMEOUT, pgPool, job.getShardKey(), job.getEntityId()));
    }

    @Test
    void givenUnknownUuids_whenDeleteJob_thenSkip() {
        final var shardKey = generateIdOperation.generateId();
        final var entityId = generateIdOperation.generateId();

        assertFalse(deleteJobOperation.deleteJob(TIMEOUT, pgPool, shardKey, entityId));
    }

    Long shardKey() {
        return generateIdOperation.generateId();
    }

    Long entityId() {
        return generateIdOperation.generateId();
    }
}