package com.omgservers.service.shard.user.service;

import com.omgservers.BaseTestClass;
import com.omgservers.service.shard.user.service.testInterface.UserServiceTestInterface;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@QuarkusTest
class UserServiceTest extends BaseTestClass {

    @Inject
    UserServiceTestInterface userService;
}