package com.omgservers.service.shard.tenant.service;

import com.omgservers.BaseTestClass;
import com.omgservers.service.shard.tenant.service.testInterface.TenantServiceTestInterface;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@QuarkusTest
class TenantServiceTest extends BaseTestClass {

    @Inject
    TenantServiceTestInterface tenantService;
}