package com.omgservers;

import com.omgservers.utils.operation.BootstrapVersionOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
public class BootstrapVersionIT extends Assertions {

    @Inject
    BootstrapVersionOperation bootstrapVersionOperation;

    @Test
    void bootstrapVersionTest() throws Exception {
        bootstrapVersionOperation.bootstrapVersion("""
                print("test")
                """);

        Thread.sleep(5000);
    }
}