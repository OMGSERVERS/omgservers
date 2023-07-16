package com.omgservers.platforms.integrationtest.operations.getConfigOperation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetConfigOperationImpl implements GetConfigOperation {

    final PlatformIntegrationTestConfig platformIntegrationTestConfig;

    @Override
    public List<URI> getExternalAddresses() {
        return platformIntegrationTestConfig.environment().servers().values().stream()
                .map(server -> server.externalAddress()).toList();
    }

    @Override
    public List<URI> getInternalAddresses() {
        return platformIntegrationTestConfig.environment().servers().values().stream()
                .map(server -> server.internalAddress()).toList();
    }

    @Override
    public Map<String, String> getServiceAccounts() {
        final var result = new HashMap<String, String>();
        platformIntegrationTestConfig.environment().servers().values().stream()
                .forEach(server -> result.put(server.serviceUsername(), server.servicePassword()));
        return result;
    }

    @Override
    public List<PlatformIntegrationTestConfig.Server> getServers() {
        return platformIntegrationTestConfig.environment().servers().values().stream().toList();
    }

    public PlatformIntegrationTestConfig getConfig() {
        return platformIntegrationTestConfig;
    }
}
