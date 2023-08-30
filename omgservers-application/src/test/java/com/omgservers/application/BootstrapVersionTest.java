package com.omgservers.application;

import com.omgservers.test.operation.BootstrapVersionOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
public class BootstrapVersionTest extends Assertions {

    @Inject
    BootstrapVersionOperation bootstrapVersionOperation;

    @Test
    void bootstrapVersionTest() throws Exception {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        bootstrapVersionOperation.bootstrapVersion("print(\"version was initialized\")");
    }
}
