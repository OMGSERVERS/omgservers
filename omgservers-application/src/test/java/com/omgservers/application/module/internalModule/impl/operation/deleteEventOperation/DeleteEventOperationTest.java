package com.omgservers.application.module.internalModule.impl.operation.deleteEventOperation;

import com.omgservers.application.module.internalModule.impl.operation.insertEventOperation.InsertEventOperation;
import com.omgservers.application.module.internalModule.model.event.body.TenantCreatedEventBodyModel;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@Slf4j
@QuarkusTest
class DeleteEventOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    DeleteEventOperation deleteEventOperation;

    @Inject
    InsertEventOperation insertEventOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenEvent_whenDeleteEvent_thenDeleted() {
        final var event = TenantCreatedEventBodyModel.createEvent(tenantUuid());
        insertEventOperation.insertEvent(TIMEOUT, pgPool, event);

        assertTrue(deleteEventOperation.deleteEvent(TIMEOUT, pgPool, event.getUuid()));
    }

    @Test
    void givenUnknownUuid_whenDeleteEvent_thenSkip() {
        final var shard = 0;
        final var uuid = UUID.randomUUID();

        assertFalse(deleteEventOperation.deleteEvent(TIMEOUT, pgPool, uuid));
    }

    UUID tenantUuid() {
        return UUID.randomUUID();
    }
}