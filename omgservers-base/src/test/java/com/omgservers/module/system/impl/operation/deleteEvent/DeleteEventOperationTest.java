package com.omgservers.module.system.impl.operation.deleteEvent;

import com.omgservers.module.system.factory.EventModelFactory;
import com.omgservers.module.system.impl.operation.upsertEvent.UpsertEventOperation;
import com.omgservers.operation.generateId.GenerateIdOperation;
import com.omgservers.model.event.body.TenantCreatedEventBodyModel;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class DeleteEventOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    DeleteEventOperation deleteEventOperation;

    @Inject
    UpsertEventOperation upsertEventOperation;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    PgPool pgPool;

    @Test
    void givenEvent_whenDeleteEvent_thenDeleted() {
        final var eventBody = new TenantCreatedEventBodyModel(tenantId());
        final var event = eventModelFactory.create(eventBody);
        upsertEventOperation.upsertEvent(TIMEOUT, pgPool, event);

        assertTrue(deleteEventOperation.deleteEvent(TIMEOUT, pgPool, event.getId()));
    }

    @Test
    void givenUnknownUuid_whenDeleteEvent_thenSkip() {
        final var id = generateIdOperation.generateId();

        assertFalse(deleteEventOperation.deleteEvent(TIMEOUT, pgPool, id));
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }
}