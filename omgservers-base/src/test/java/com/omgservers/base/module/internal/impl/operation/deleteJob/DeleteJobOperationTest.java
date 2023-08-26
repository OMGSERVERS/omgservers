package com.omgservers.base.module.internal.impl.operation.deleteJob;

import com.omgservers.base.module.internal.impl.operation.upsertJob.UpsertJobOperation;
import com.omgservers.base.factory.JobModelFactory;
import com.omgservers.model.job.JobType;
import com.omgservers.base.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class DeleteJobOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

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
        final var job = jobModelFactory.create(shardKey(), entity(), JobType.RUNTIME);
        upsertJobOperation.upsertJob(TIMEOUT, pgPool, job);

        assertTrue(deleteJobOperation.deleteJob(TIMEOUT, pgPool, job.getShardKey(), job.getEntity()));
    }

    @Test
    void givenUnknownUuids_whenDeleteJob_thenSkip() {
        final var shardKey = generateIdOperation.generateId();
        final var entity = generateIdOperation.generateId();

        assertFalse(deleteJobOperation.deleteJob(TIMEOUT, pgPool, shardKey, entity));
    }

    Long shardKey() {
        return generateIdOperation.generateId();
    }

    Long entity() {
        return generateIdOperation.generateId();
    }
}