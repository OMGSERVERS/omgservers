package com.omgservers.tester.operation.bootstrapTestClient;

import com.omgservers.tester.model.TestClientModel;
import com.omgservers.tester.model.TestVersionModel;

import java.io.IOException;

public interface BootstrapTestClientOperation {

    TestClientModel bootstrapTestClient(TestVersionModel testVersion) throws IOException;
}
