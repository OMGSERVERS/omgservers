package com.omgservers.service.operation.upsertEvent;

import com.omgservers.service.factory.EventModelFactory;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import com.omgservers.model.event.body.TenantCreatedEventBodyModel;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertEventOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    UpsertEventOperation upsertEventOperation;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    PgPool pgPool;

    @Test
    void givenEvent_whenUpsertEvent_thenInserted() {
        final var eventBody = new TenantCreatedEventBodyModel(tenantId());
        final var event = eventModelFactory.create(eventBody);
        assertTrue(upsertEventOperation.upsertEvent(TIMEOUT, pgPool, event));
    }

    @Test
    void givenEvent_whenUpsertEvent_thenUpdated() {
        final var eventBody = new TenantCreatedEventBodyModel(tenantId());
        final var event = eventModelFactory.create(eventBody);
        upsertEventOperation.upsertEvent(TIMEOUT, pgPool, event);
        assertFalse(upsertEventOperation.upsertEvent(TIMEOUT, pgPool, event));
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }
}