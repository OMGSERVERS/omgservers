package com.omgservers.test.operations.getDeveloperClientOperation;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
class GetDeveloperClientOperationImpl implements GetDeveloperClientOperation {

    final Map<URI, DeveloperClientForAnonymousAccess> anonymousAccessClients;
    final Map<URI, DeveloperClientForAuthenticatedAccess> developerAccessClients;

    GetDeveloperClientOperationImpl() {
        anonymousAccessClients = new ConcurrentHashMap<>();
        developerAccessClients = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized DeveloperClientForAnonymousAccess getDeveloperClientForAnonymousAccess(URI uri) {
        if (uri == null) {
            throw new IllegalArgumentException("uri is null");
        }

        if (!anonymousAccessClients.containsKey(uri)) {
            var client = RestClientBuilder.newBuilder()
                    .baseUri(uri)
                    .build(DeveloperClientForAnonymousAccess.class);
            anonymousAccessClients.put(uri, client);
            log.info("Client was created and cached, uri={}", uri);
        }

        return anonymousAccessClients.get(uri);
    }

    @Override
    public synchronized DeveloperClientForAuthenticatedAccess getDeveloperClientForAuthenticatedAccess(URI uri) {
        if (uri == null) {
            throw new IllegalArgumentException("uri is null");
        }

        if (!developerAccessClients.containsKey(uri)) {
            var client = RestClientBuilder.newBuilder()
                    .baseUri(uri)
                    .build(DeveloperClientForAuthenticatedAccess.class);
            developerAccessClients.put(uri, client);
            log.info("Client was created and cached, uri={}", uri);
        }

        return developerAccessClients.get(uri);
    }
}
