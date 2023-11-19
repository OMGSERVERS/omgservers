package com.omgservers.tester.operation.bootstrapService;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

public interface BootstrapServiceOperation {

    void bootstrapService(List<URI> servers, Map<String, String> serviceAccounts) throws IOException;
}
