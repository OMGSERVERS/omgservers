package com.omgservers.application.module.internalModule.impl.operation.selectEventOperation;

import com.omgservers.application.exception.ServerSideNotFoundException;
import com.omgservers.application.module.internalModule.impl.operation.insertEventOperation.InsertEventOperation;
import com.omgservers.application.module.internalModule.model.event.body.TenantCreatedEventBodyModel;
import com.omgservers.application.operation.generateIdOperation.GenerateIdOperation;
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
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenEvent_whenSelectEvent_thenSelected() {
        // TODO: fix
//        final var event1 = TenantCreatedEventBodyModel.createEvent(tenantId());
//        insertEventOperation.insertEvent(TIMEOUT, pgPool, event1);
//
//        final var event2 = selectEventOperation.selectEvent(TIMEOUT, pgPool, event1.getUuid());
//        assertEquals(event1, event2);
    }

    @Test
    void givenUnknownUuid_whenSelectEvent_thenServerSideNotFoundException() {
        final var id = generateIdOperation.generateId();

        assertThrows(ServerSideNotFoundException.class, () -> selectEventOperation
                .selectEvent(TIMEOUT, pgPool, id));
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }
}