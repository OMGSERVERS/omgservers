package com.omgservers.application.module.internalModule.impl.operation.insertEventOperation;

import com.omgservers.application.exception.ServerSideConflictException;
import com.omgservers.application.module.internalModule.impl.operation.selectEventOperation.SelectEventOperation;
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
class InsertEventOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    InsertEventOperation insertEventOperation;

    @Inject
    SelectEventOperation selectEventOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenEvent_whenInsertEvent_thenInserted() {
        final var event1 = TenantCreatedEventBodyModel.createEvent(tenantUuid());
        insertEventOperation.insertEvent(TIMEOUT, pgPool, event1);

        final var event2 = selectEventOperation.selectEvent(TIMEOUT, pgPool, event1.getUuid());
        assertEquals(event1, event2);
    }

    @Test
    void givenEvent_whenInsertEvent_thenServerSideConflictException() {
        final var event = TenantCreatedEventBodyModel.createEvent(tenantUuid());
        insertEventOperation.insertEvent(TIMEOUT, pgPool, event);

        final var exception = assertThrows(ServerSideConflictException.class, () -> insertEventOperation
                .insertEvent(TIMEOUT, pgPool, event));
        log.info("Exception: {}", exception.getMessage());
    }

    UUID tenantUuid() {
        return UUID.randomUUID();
    }
}