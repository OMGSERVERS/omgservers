package com.omgservers.service.module.user.impl.service.userService;

import com.omgservers.BaseTestClass;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@QuarkusTest
class UserServiceTest extends BaseTestClass {

    @Inject
    UserService userService;
}