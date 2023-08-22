package com.omgservers.application.module.internalModule.impl.operation.insertEventOperation;

import com.omgservers.application.module.internalModule.impl.operation.selectEventOperation.SelectEventOperation;
import com.omgservers.application.operation.generateIdOperation.GenerateIdOperation;
import com.omgservers.application.operation.insertEventOperation.InsertEventOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class InsertEventOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    InsertEventOperation insertEventOperation;

    @Inject
    SelectEventOperation selectEventOperation;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenEvent_whenInsertEvent_thenInserted() {
        // TODO: fix
//        final var event1 = TenantCreatedEventBodyModel.createEvent(tenantId());
//        insertEventOperation.insertEvent(TIMEOUT, pgPool, event1);
//
//        final var event2 = selectEventOperation.selectEvent(TIMEOUT, pgPool, event1.getUuid());
//        assertEquals(event1, event2);
    }

    @Test
    void givenEvent_whenInsertEvent_thenServerSideConflictException() {
        // TODO: fix
//        final var event = TenantCreatedEventBodyModel.createEvent(tenantId());
//        insertEventOperation.insertEvent(TIMEOUT, pgPool, event);
//
//        final var exception = assertThrows(ServerSideConflictException.class, () -> insertEventOperation
//                .insertEvent(TIMEOUT, pgPool, event));
//        log.info("Exception: {}", exception.getMessage());
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }
}