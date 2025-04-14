package com.omgservers.service.handler.root;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.shard.root.root.DeleteRootRequest;
import com.omgservers.service.event.body.module.root.RootDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.root.testInterface.RootDeletedEventHandlerImplTestInterface;
import com.omgservers.service.shard.root.service.testInterface.RootServiceTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class RootDeletedEventHandlerImplTest extends BaseTestClass {

    @Inject
    RootDeletedEventHandlerImplTestInterface rootDeletedEventHandler;

    @Inject
    RootServiceTestInterface rootService;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenRoot_whenRetry_thenFinished() {
        final var root = testDataFactory.getRootTestDataFactory().createRoot();

        final var rootId = root.getId();

        final var deleteRootRequest = new DeleteRootRequest(rootId);
        rootService.deleteRoot(deleteRootRequest);

        final var eventBody = new RootDeletedEventBodyModel(rootId);
        final var eventModel = eventModelFactory.create(eventBody);

        rootDeletedEventHandler.handle(eventModel);
        log.info("Retry");
        rootDeletedEventHandler.handle(eventModel);
    }
}