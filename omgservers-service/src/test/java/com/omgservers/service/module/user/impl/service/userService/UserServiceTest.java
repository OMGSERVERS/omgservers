package com.omgservers.service.module.user.impl.service.userService;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;

@Slf4j
@QuarkusTest
class UserServiceTest extends Assertions {

    @Inject
    UserService userService;
}