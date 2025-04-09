package com.omgservers.service.server.cache.impl.service.inmemory.component;

import com.omgservers.BaseTestClass;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.Instant;

@Slf4j
@QuarkusTest
class InMemoryCacheTest extends BaseTestClass {

    @Inject
    InMemoryCache inMemoryCache;

    @Test
    void getInstantTest() {
        final var cacheKey = "instant";
        final var cacheValue = Instant.now();
        inMemoryCache.put(cacheKey, cacheValue, Instant.now().plusSeconds(1));
        final var value = inMemoryCache.getInstant(cacheKey);
        assertEquals(cacheValue, value);
    }

}