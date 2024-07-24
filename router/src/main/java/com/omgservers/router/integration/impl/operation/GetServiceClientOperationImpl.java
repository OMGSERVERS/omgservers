package com.omgservers.router.integration.impl.operation;

import io.quarkus.rest.client.reactive.QuarkusRestClientBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
class GetServiceClientOperationImpl implements GetServiceClientOperation {

    final Map<URI, ServiceClient> cache;

    GetServiceClientOperationImpl() {
        cache = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized ServiceClient getClient(final URI uri) {
        if (!cache.containsKey(uri)) {
            final var client = QuarkusRestClientBuilder.newBuilder()
                    .baseUri(uri)
                    .build(ServiceClient.class);

            log.info("Service client was created, uri={}", uri);

            cache.put(uri, client);
        }
        return cache.get(uri);
    }

    @Override
    public Boolean hasCacheFor(final URI uri) {
        if (uri == null) {
            throw new IllegalArgumentException("uri is null");
        }

        return cache.containsKey(uri);
    }

    @Override
    public Integer sizeOfCache() {
        return cache.size();
    }
}
