package com.omgservers.service.handler.pool;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.shard.pool.poolContainer.DeletePoolContainerRequest;
import com.omgservers.service.event.body.module.pool.PoolContainerDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.pool.testInterface.PoolContainerCreatedEventHandlerImplTestInterface;
import com.omgservers.service.handler.pool.testInterface.PoolContainerDeletedEventHandlerImplTestInterface;
import com.omgservers.service.shard.pool.service.testInterface.PoolServiceTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class PoolContainerDeletedEventHandlerImplTest extends BaseTestClass {

    @Inject
    PoolContainerDeletedEventHandlerImplTestInterface poolContainerDeletedEventHandler;

    @Inject
    PoolContainerCreatedEventHandlerImplTestInterface poolContainerCreatedEventHandler;

    @Inject
    PoolServiceTestInterface poolService;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    @Disabled
    void givenHandler_whenRetry_thenFinished() {
        final var testData = testDataFactory.createDefaultTestData();
        final var poolContainer = testData.getLobbyPoolContainer();
        final var poolId = poolContainer.getPoolId();
        final var id = poolContainer.getId();

        final var deletePoolContainerRequest = new DeletePoolContainerRequest(poolId, id);
        poolService.execute(deletePoolContainerRequest);

        final var eventBody = new PoolContainerDeletedEventBodyModel(poolId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        poolContainerDeletedEventHandler.handle(eventModel);
        log.info("Retry");
        poolContainerDeletedEventHandler.handle(eventModel);
    }
}