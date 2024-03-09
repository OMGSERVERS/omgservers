package com.omgservers.tester.operation.bootstrapTestClient;

import com.omgservers.tester.model.TestClientModel;
import com.omgservers.tester.model.TestVersionModel;

import java.io.IOException;

public interface BootstrapTestClientOperation {

    /**
     * Bootstrap new client for a new user.
     */
    TestClientModel bootstrapTestClient(TestVersionModel testVersion) throws IOException;

    /**
     * Bootstrap new client for an existing user.
     */
    TestClientModel bootstrapTestClient(TestVersionModel testVersion, TestClientModel testClient) throws IOException;
}
