package com.omgservers.test.operations.getTenantServiceApiClientOperation;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import jakarta.enterprise.context.ApplicationScoped;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
class GetTenantServiceApiClientOperationImpl implements GetTenantServiceApiClientOperation {

    final Map<URI, TenantServiceApiClient> cache;

    GetTenantServiceApiClientOperationImpl() {
        cache = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized TenantServiceApiClient getClient(URI uri) {
        if (uri == null) {
            throw new IllegalArgumentException("uri is null");
        }

        if (!cache.containsKey(uri)) {
            var client = RestClientBuilder.newBuilder()
                    .baseUri(uri)
                    .build(TenantServiceApiClient.class);
            cache.put(uri, client);
            log.info("Client was created and cached, uri={}", uri);
        }
        return cache.get(uri);
    }
}
