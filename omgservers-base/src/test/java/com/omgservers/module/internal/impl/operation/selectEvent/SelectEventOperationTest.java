package com.omgservers.module.internal.impl.operation.selectEvent;

import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.event.body.TenantCreatedEventBodyModel;
import com.omgservers.module.internal.factory.EventModelFactory;
import com.omgservers.module.internal.impl.operation.upsertEvent.UpsertEventOperation;
import com.omgservers.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class SelectEventOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    SelectEventOperation selectEventOperation;

    @Inject
    UpsertEventOperation upsertEventOperation;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    PgPool pgPool;

    @Test
    void givenEvent_whenSelectEvent_thenSelected() {
        final var eventBody = new TenantCreatedEventBodyModel(tenantId());
        final var event1 = eventModelFactory.create(eventBody);
        upsertEventOperation.upsertEvent(TIMEOUT, pgPool, event1);

        final var event2 = selectEventOperation.selectEvent(TIMEOUT, pgPool, event1.getId());
        assertEquals(event1, event2);
    }

    @Test
    void givenUnknownUuid_whenSelectEvent_thenNotFound() {
        final var id = generateIdOperation.generateId();

        assertThrows(ServerSideNotFoundException.class, () -> selectEventOperation
                .selectEvent(TIMEOUT, pgPool, id));
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }
}