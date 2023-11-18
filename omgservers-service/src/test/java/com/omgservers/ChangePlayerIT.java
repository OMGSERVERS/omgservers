package com.omgservers;

import com.omgservers.tester.test.ChangePlayerTest;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URI;

@Slf4j
@QuarkusTest
public class ChangePlayerIT extends Assertions {

    @TestHTTPResource("/omgservers/gateway")
    URI uri;

    @Inject
    ChangePlayerTest changePlayerTest;

    @Test
    void changePlayerIT() throws Exception {
        changePlayerTest.testChangePlayer(uri);
    }
}
