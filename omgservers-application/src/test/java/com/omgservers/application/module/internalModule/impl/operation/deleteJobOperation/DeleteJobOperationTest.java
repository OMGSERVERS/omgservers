package com.omgservers.application.module.internalModule.impl.operation.deleteJobOperation;

import com.omgservers.application.module.internalModule.impl.operation.upsertJobOperation.UpsertJobOperation;
import com.omgservers.application.module.internalModule.model.job.JobModel;
import com.omgservers.application.module.internalModule.model.job.JobType;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@Slf4j
@QuarkusTest
class DeleteJobOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    DeleteJobOperation deleteJobOperation;

    @Inject
    UpsertJobOperation upsertJobOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenJob_whenDeleteJob_thenDeleted() {
        final var job = JobModel.create(shardKey(), entity(), JobType.RUNTIME);
        upsertJobOperation.upsertJob(TIMEOUT, pgPool, job);

        assertTrue(deleteJobOperation.deleteJob(TIMEOUT, pgPool, job.getShardKey(), job.getEntity()));
    }

    @Test
    void givenUnknownUuids_whenDeleteJob_thenSkip() {
        final var shardKey = UUID.randomUUID();
        final var entity = UUID.randomUUID();

        assertFalse(deleteJobOperation.deleteJob(TIMEOUT, pgPool, shardKey, entity));
    }

    UUID shardKey() {
        return UUID.randomUUID();
    }

    UUID entity() {
        return UUID.randomUUID();
    }
}