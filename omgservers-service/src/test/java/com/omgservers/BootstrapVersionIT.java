package com.omgservers;

import com.omgservers.tester.test.BootstrapVersionTest;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
public class BootstrapVersionIT extends Assertions {

    @Inject
    BootstrapVersionTest bootstrapVersionTest;

    @Test
    void bootstrapVersionIT() throws Exception {
        bootstrapVersionTest.testBootstrapVersion();
    }
}
