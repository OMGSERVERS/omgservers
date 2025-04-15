package com.omgservers.service.configuration;

import com.omgservers.BaseTestClass;
import io.quarkus.test.junit.QuarkusTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

@Slf4j
@QuarkusTest
class LockQualifierEnumTest extends BaseTestClass {

    @Test
    void testKeyUniqueness() {
        final var unique = new HashSet<>();
        unique.add(LockQualifierEnum.BOOTSTRAP.key);
        unique.add(LockQualifierEnum.EVENT_HANDLER.key);
        unique.add(LockQualifierEnum.SCHEDULER.key);

        assertEquals(3, unique.size());
    }

}