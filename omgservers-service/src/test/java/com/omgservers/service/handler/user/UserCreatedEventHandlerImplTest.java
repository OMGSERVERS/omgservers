package com.omgservers.service.handler.user;

import com.omgservers.BaseTestClass;
import com.omgservers.service.event.body.module.user.UserCreatedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.user.testInterface.UserCreatedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UserCreatedEventHandlerImplTest extends BaseTestClass {

    @Inject
    UserCreatedEventHandlerImplTestInterface userCreatedEventHandler;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var user = testDataFactory.getUserTestDataFactory().createPlayerUser("password");

        final var userId = user.getId();

        final var eventBody = new UserCreatedEventBodyModel(userId);
        final var eventModel = eventModelFactory.create(eventBody);

        userCreatedEventHandler.handle(eventModel);
        log.info("Retry");
        userCreatedEventHandler.handle(eventModel);
    }
}