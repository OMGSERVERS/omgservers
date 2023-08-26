package com.omgservers.application.module.internalModule.impl.service.eventHelpService.impl;

import com.omgservers.base.module.internal.impl.Dispatcher;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class DispatcherInMemoryCacheTest extends Assertions {

    @Inject
    Dispatcher dispatcherInMemoryCache;

    @Test
    void test() {
        dispatcherInMemoryCache.addEvent(0L, 100L);
        dispatcherInMemoryCache.addEvent(1L, 200L);
        dispatcherInMemoryCache.addEvent(2L, 100L);
        dispatcherInMemoryCache.addEvent(3L, 300L);
        dispatcherInMemoryCache.addEvent(4L, 300L);
        dispatcherInMemoryCache.addEvent(5L, 200L);
        dispatcherInMemoryCache.addEvent(6L, 100L);
        dispatcherInMemoryCache.addEvent(7L, 200L);

        final var g1 = dispatcherInMemoryCache.pollGroup();
        assertEquals(0L, g1.getEventId());
        assertEquals(100L, g1.getGroupId());
    }
}