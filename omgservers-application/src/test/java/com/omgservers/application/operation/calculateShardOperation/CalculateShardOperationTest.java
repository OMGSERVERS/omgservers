package com.omgservers.application.operation.calculateShardOperation;

import com.omgservers.base.impl.operation.calculateShardOperation.CalculateShardOperation;
import io.quarkus.test.junit.QuarkusTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

@Slf4j
@QuarkusTest
class CalculateShardOperationTest extends Assertions {

    @Inject
    CalculateShardOperation calculateShardOperation;

    @Test
    void givenSingleHashKey_whenCalculateShard() {
        final var indexShardCount = 32768;
        final var shard = calculateShardOperation.calculateShard(indexShardCount, "uuid");
        assertNotNull(shard);
        assertTrue(shard >= 0 && shard < indexShardCount);
    }

    @Test
    void givenCompositeHashKey_whenCalculateShard() {
        final var indexShardCount = 32768;
        final var shard = calculateShardOperation.calculateShard(indexShardCount, "tenant", "username");
        assertNotNull(shard);
        assertTrue(shard >= 0 && shard < indexShardCount);
    }
}