package com.omgservers.service.service.cache.impl.operation;

import com.omgservers.BaseTestClass;
import com.omgservers.service.operation.server.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class GetClientLastActivityCacheKeyOperationImplTest extends BaseTestClass {

    @Inject
    GetClientLastActivityCacheKeyOperation getClientLastActivityCacheKeyOperation;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void executeTest() {
        final var clientId = generateIdOperation.generateId();
        final var cacheKey = getClientLastActivityCacheKeyOperation.execute(clientId);
        assertEquals("service:client:" + clientId + ":last-activity", cacheKey);
    }
}