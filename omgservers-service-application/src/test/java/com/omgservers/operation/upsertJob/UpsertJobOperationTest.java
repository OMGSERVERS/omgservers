package com.omgservers.operation.upsertJob;

import com.omgservers.model.job.JobQualifierEnum;
import com.omgservers.module.system.factory.JobModelFactory;
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
        final var job = jobModelFactory.create(shardKey(), entityId(), JobQualifierEnum.TENANT);
        assertTrue(upsertJobOperation.upsertJob(TIMEOUT, pgPool, job));
    }

    @Test
    void givenJob_whenUpsertJob_thenUpdated() {
        final var job = jobModelFactory.create(shardKey(), entityId(), JobQualifierEnum.TENANT);
        upsertJobOperation.upsertJob(TIMEOUT, pgPool, job);

        assertFalse(upsertJobOperation.upsertJob(TIMEOUT, pgPool, job));
    }

    Long shardKey() {
        return generateIdOperation.generateId();
    }

    Long entityId() {
        return generateIdOperation.generateId();
    }
}