package com.omgservers.tester.operation.bootstrapTestClient;

import com.omgservers.tester.dto.TestClientDto;
import com.omgservers.tester.dto.TestVersionDto;

import java.io.IOException;

public interface BootstrapTestClientOperation {

    /**
     * Bootstrap a new client for a new user.
     */
    TestClientDto bootstrapTestClient(TestVersionDto testVersion) throws IOException;

    /**
     * Bootstrap a new client for an existing user.
     */
    TestClientDto bootstrapTestClient(TestVersionDto testVersion, TestClientDto testClient) throws IOException;
}
