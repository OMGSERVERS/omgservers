package com.omgservers.application.module.internalModule.impl.operation.deleteEventOperation;

import com.omgservers.application.operation.insertEventOperation.InsertEventOperation;
import com.omgservers.application.operation.generateIdOperation.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class DeleteEventOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    DeleteEventOperation deleteEventOperation;

    @Inject
    InsertEventOperation insertEventOperation;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenEvent_whenDeleteEvent_thenDeleted() {
        // TODO: fix it
//        final var event = TenantCreatedEventBodyModel.createEvent(tenantId());
//        insertEventOperation.insertEvent(TIMEOUT, pgPool, event);
//
//        assertTrue(deleteEventOperation.deleteEvent(TIMEOUT, pgPool, event.getUuid()));
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