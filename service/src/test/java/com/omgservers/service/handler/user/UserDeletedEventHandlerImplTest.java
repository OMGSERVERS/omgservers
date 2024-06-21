package com.omgservers.service.handler.user;

import com.omgservers.model.dto.user.DeleteUserRequest;
import com.omgservers.model.event.body.module.user.UserDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.user.testInterface.UserDeletedEventHandlerImplTestInterface;
import com.omgservers.service.module.user.impl.service.userService.testInterface.UserServiceTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UserDeletedEventHandlerImplTest extends Assertions {

    @Inject
    UserDeletedEventHandlerImplTestInterface userDeletedEventHandler;

    @Inject
    UserServiceTestInterface userService;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var user = testDataFactory.getUserTestDataFactory().createPlayerUser("password");

        final var userId = user.getId();

        final var deleteUserRequest = new DeleteUserRequest(userId);
        userService.deleteUser(deleteUserRequest);

        final var eventBody = new UserDeletedEventBodyModel(userId);
        final var eventModel = eventModelFactory.create(eventBody);

        userDeletedEventHandler.handle(eventModel);
        log.info("Retry");
        userDeletedEventHandler.handle(eventModel);
    }
}