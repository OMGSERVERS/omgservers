package com.omgservers.service.handler.impl.pool;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.module.pool.pool.DeletePoolRequest;
import com.omgservers.service.event.body.module.pool.PoolDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.impl.pool.testInterface.PoolDeletedEventHandlerImplTestInterface;
import com.omgservers.service.module.pool.impl.service.poolService.testInterface.PoolServiceTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class PoolDeletedEventHandlerImplTest extends BaseTestClass {

    @Inject
    PoolDeletedEventHandlerImplTestInterface poolDeletedEventHandlerImplTestInterface;

    @Inject
    PoolServiceTestInterface poolService;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var pool = testDataFactory.getPoolTestDataFactory().createPool();

        final var poolId = pool.getId();

        final var deletePoolRequest = new DeletePoolRequest(poolId);
        poolService.execute(deletePoolRequest);

        final var eventBody = new PoolDeletedEventBodyModel(poolId);
        final var eventModel = eventModelFactory.create(eventBody);

        poolDeletedEventHandlerImplTestInterface.handle(eventModel);
        log.info("Retry");
        poolDeletedEventHandlerImplTestInterface.handle(eventModel);
    }
}