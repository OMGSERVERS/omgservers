package com.omgservers.test.operations.getConfigOperation;

import java.net.URI;
import java.util.List;
import java.util.Map;

public interface GetConfigOperation {

    List<URI> getExternalAddresses();

    List<URI> getInternalAddresses();

    Map<String, String> getServiceAccounts();

    List<PlatformIntegrationTestConfig.Server> getServers();

    PlatformIntegrationTestConfig getConfig();
}
