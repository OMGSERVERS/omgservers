package com.omgservers.service.handler.impl.pool;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.module.pool.poolServer.DeletePoolServerRequest;
import com.omgservers.service.event.body.module.pool.PoolServerDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.impl.pool.testInterface.PoolServerDeletedEventHandlerImplTestInterface;
import com.omgservers.service.module.pool.impl.service.poolService.testInterface.PoolServiceTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class PoolServerDeletedEventHandlerImplTest extends BaseTestClass {

    @Inject
    PoolServerDeletedEventHandlerImplTestInterface poolServerDeletedEventHandler;

    @Inject
    PoolServiceTestInterface poolService;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var defaultPool = testDataFactory.getPoolTestDataFactory().createDefaultPool();
        final var poolServer = testDataFactory.getPoolTestDataFactory().createPoolServer(defaultPool);

        final var poolId = poolServer.getPoolId();
        final var id = poolServer.getId();

        final var deletePoolServerRequest = new DeletePoolServerRequest(poolId, id);
        poolService.deletePoolServer(deletePoolServerRequest);

        final var eventBody = new PoolServerDeletedEventBodyModel(poolId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        poolServerDeletedEventHandler.handle(eventModel);
        log.info("Retry");
        poolServerDeletedEventHandler.handle(eventModel);
    }
}