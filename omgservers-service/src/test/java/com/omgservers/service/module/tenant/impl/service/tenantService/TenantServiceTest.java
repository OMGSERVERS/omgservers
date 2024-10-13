package com.omgservers.service.module.tenant.impl.service.tenantService;

import com.omgservers.BaseTestClass;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@QuarkusTest
class TenantServiceTest extends BaseTestClass {

    @Inject
    TenantService tenantService;
}