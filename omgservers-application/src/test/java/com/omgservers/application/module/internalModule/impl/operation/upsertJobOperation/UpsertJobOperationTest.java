package com.omgservers.application.module.internalModule.impl.operation.upsertJobOperation;

import com.omgservers.base.factory.JobModelFactory;
import com.omgservers.base.impl.operation.upsertJobOperation.UpsertJobOperation;
import com.omgservers.model.job.JobType;
import com.omgservers.base.impl.operation.generateIdOperation.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertJobOperationTest extends Assertions {
    final long TIMEOUT = 1L;

    @Inject
    UpsertJobOperation upsertJobOperation;

    @Inject
    JobModelFactory jobModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenJob_whenUpsertJob_thenInserted() {
        final var job = jobModelFactory.create(shardKey(), entity(), JobType.RUNTIME);
        assertTrue(upsertJobOperation.upsertJob(TIMEOUT, pgPool, job));
    }

    @Test
    void givenJob_whenUpsertJob_thenUpdated() {
        final var job = jobModelFactory.create(shardKey(), entity(), JobType.RUNTIME);
        upsertJobOperation.upsertJob(TIMEOUT, pgPool, job);

        assertFalse(upsertJobOperation.upsertJob(TIMEOUT, pgPool, job));
    }

    Long shardKey() {
        return generateIdOperation.generateId();
    }

    Long entity() {
        return generateIdOperation.generateId();
    }
}