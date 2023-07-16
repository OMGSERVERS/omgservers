package com.omgservers.application.module.internalModule.impl.operation.selectEventOperation;

import com.omgservers.application.exception.ServerSideNotFoundException;
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
class SelectEventOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    SelectEventOperation selectEventOperation;

    @Inject
    InsertEventOperation insertEventOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenEvent_whenSelectEvent_thenSelected() {
        final var event1 = TenantCreatedEventBodyModel.createEvent(tenantUuid());
        insertEventOperation.insertEvent(TIMEOUT, pgPool, event1);

        final var event2 = selectEventOperation.selectEvent(TIMEOUT, pgPool, event1.getUuid());
        assertEquals(event1, event2);
    }

    @Test
    void givenUnknownUuid_whenSelectEvent_thenServerSideNotFoundException() {
        final var shard = 0;
        final var uuid = UUID.randomUUID();

        assertThrows(ServerSideNotFoundException.class, () -> selectEventOperation
                .selectEvent(TIMEOUT, pgPool, uuid));
    }

    UUID tenantUuid() {
        return UUID.randomUUID();
    }
}