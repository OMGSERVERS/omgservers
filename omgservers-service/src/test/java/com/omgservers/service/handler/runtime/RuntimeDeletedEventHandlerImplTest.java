package com.omgservers.service.handler.runtime;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.shard.runtime.runtime.DeleteRuntimeRequest;
import com.omgservers.service.event.body.module.runtime.RuntimeDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.runtime.testInterface.RuntimeDeletedEventHandlerImplTestInterface;
import com.omgservers.service.shard.runtime.service.testInterface.RuntimeServiceTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class RuntimeDeletedEventHandlerImplTest extends BaseTestClass {

    @Inject
    RuntimeDeletedEventHandlerImplTestInterface runtimeDeletedEventHandler;

    @Inject
    RuntimeServiceTestInterface runtimeService;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var testData = testDataFactory.createDefaultTestData();

        final var lobbyRuntime = testData.getLobbyRuntime();

        final var runtimeId = lobbyRuntime.getId();

        final var deleteRuntimeRequest = new DeleteRuntimeRequest(runtimeId);
        runtimeService.execute(deleteRuntimeRequest);

        final var eventBody = new RuntimeDeletedEventBodyModel(runtimeId);
        final var eventModel = eventModelFactory.create(eventBody);

        runtimeDeletedEventHandler.handle(eventModel);
        log.info("Retry");
        runtimeDeletedEventHandler.handle(eventModel);
    }
}