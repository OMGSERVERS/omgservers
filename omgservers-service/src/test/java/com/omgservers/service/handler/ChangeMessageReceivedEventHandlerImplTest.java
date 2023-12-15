package com.omgservers.service.handler;

import com.omgservers.model.event.body.ChangeMessageReceivedEventBodyModel;
import com.omgservers.service.factory.EventModelFactory;
import com.omgservers.service.module.matchmaker.operation.testOperation.CreateTestMatchmakerOperation;
import com.omgservers.service.module.runtime.operation.testOperation.CreateTestRuntimeOperation;
import com.omgservers.service.module.tenant.operation.testOperation.CreateTestVersionOperation;
import com.omgservers.service.module.user.operation.testOperation.CreateTestClientOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;

@Slf4j
@QuarkusTest
class ChangeMessageReceivedEventHandlerImplTest extends Assertions {

    @Inject
    ChangeMessageReceivedEventHandlerImpl changeMessageReceivedEventHandler;

    @Inject
    CreateTestVersionOperation createTestVersionOperation;

    @Inject
    CreateTestMatchmakerOperation createTestMatchmakerOperation;

    @Inject
    CreateTestRuntimeOperation createTestRuntimeOperation;

    @Inject
    CreateTestClientOperation createTestClientOperation;

    @Inject
    EventModelFactory eventModelFactory;

    @Test
    void givenTestClient_whenHandleChangeMessageReceivedEventAgain_thenOk() {
        final var testVersionHolder = createTestVersionOperation.createTestVersion();

        final var testMatchmaker = createTestMatchmakerOperation
                .createTestMatchmaker(testVersionHolder.tenant().getId(), testVersionHolder.version().getId());

        final var testRuntime = createTestRuntimeOperation
                .createTestRuntime(testVersionHolder.tenant().getId(), testVersionHolder.version().getId());

        final var testClientHolder = createTestClientOperation.createTestClient(
                testVersionHolder.tenant().getId(),
                testVersionHolder.stage().getId(),
                testVersionHolder.version().getId(),
                testMatchmaker.getId(),
                testRuntime.getId());

        final var eventBody = new ChangeMessageReceivedEventBodyModel(
                testVersionHolder.tenant().getId(),
                testVersionHolder.stage().getId(),
                testClientHolder.user().getId(),
                testClientHolder.player().getId(),
                testClientHolder.client().getId(),
                "message");

        final var eventModel = eventModelFactory.create(eventBody);
        changeMessageReceivedEventHandler.handle(eventModel).await().atMost(Duration.ofSeconds(1));
        changeMessageReceivedEventHandler.handle(eventModel).await().atMost(Duration.ofSeconds(1));
    }
}