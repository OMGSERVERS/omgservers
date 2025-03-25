package com.omgservers.service.shard.deployment.service;

import com.omgservers.BaseTestClass;
import com.omgservers.service.shard.deployment.service.testInterface.DeploymentServiceTestInterface;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@QuarkusTest
class DeploymentServiceTest extends BaseTestClass {

    @Inject
    DeploymentServiceTestInterface deploymentService;
}