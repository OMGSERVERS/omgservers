package com.omgservers.service.operation;

import com.omgservers.BaseTestClass;
import com.omgservers.service.operation.getServiceConfig.GetServiceConfigOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class GetServiceConfigOperationTest extends BaseTestClass {

    @Inject
    GetServiceConfigOperation getServiceConfigOperation;

    @Test
    void givenNotNullConfig() {
        assertNotNull(getServiceConfigOperation.getServiceConfig());
    }
}