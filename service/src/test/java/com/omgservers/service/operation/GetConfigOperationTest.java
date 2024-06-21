package com.omgservers.service.operation;

import com.omgservers.service.operation.getConfig.GetConfigOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class GetConfigOperationTest extends Assertions {

    @Inject
    GetConfigOperation getConfigOperation;

    @Test
    void givenNotNullConfig() {
        assertNotNull(getConfigOperation.getServiceConfig());
    }
}