package com.omgservers.application.module.internalModule.impl.operation.upsertJobOperation;

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
class UpsertJobOperationTest extends Assertions {
    final long TIMEOUT = 1L;

    @Inject
    UpsertJobOperation upsertJobOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenJob_whenUpsertJob_thenInserted() {
        final var job = JobModel.create(shardKey(), entity(), JobType.RUNTIME);
        assertTrue(upsertJobOperation.upsertJob(TIMEOUT, pgPool, job));
    }

    @Test
    void givenJob_whenUpsertJob_thenUpdated() {
        final var job = JobModel.create(shardKey(), entity(), JobType.RUNTIME);
        upsertJobOperation.upsertJob(TIMEOUT, pgPool, job);

        assertFalse(upsertJobOperation.upsertJob(TIMEOUT, pgPool, job));
    }

    UUID shardKey() {
        return UUID.randomUUID();
    }

    UUID entity() {
        return UUID.randomUUID();
    }
}