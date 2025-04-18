package com.omgservers.service.operation.server;

import com.omgservers.BaseTestClass;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class CalculateShardOperationTest extends BaseTestClass {

    @Inject
    CalculateShardOperation calculateShardOperation;

    @Inject
    GetServiceConfigOperation getServiceConfigOperation;

    @Test
    void givenSingleHashKey_whenCalculateShard() {
        final var shardModel = calculateShardOperation.execute("key").await().indefinitely();
        final var slotsCount = getServiceConfigOperation.getServiceConfig().server().slotsCount();
        final var slot = shardModel.slot();
        assertNotNull(shardModel);
        assertTrue(slot >= 0 && slot < slotsCount);
    }
}