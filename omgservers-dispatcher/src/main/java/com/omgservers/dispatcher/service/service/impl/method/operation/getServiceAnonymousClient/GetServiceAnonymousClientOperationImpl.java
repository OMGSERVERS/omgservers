package com.omgservers.dispatcher.service.service.impl.method.operation.getServiceAnonymousClient;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
class GetServiceAnonymousClientOperationImpl
        implements GetServiceAnonymousClientOperation {

    final Map<URI, ServiceAnonymousModuleClient> cache;

    GetServiceAnonymousClientOperationImpl() {
        cache = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized ServiceAnonymousModuleClient getClient(final URI uri) {
        if (!cache.containsKey(uri)) {
            final var client = RestClientBuilder.newBuilder()
                    .baseUri(uri)
                    .build(ServiceAnonymousModuleClient.class);

            log.debug("Service anonymous client was created, uri={}", uri);

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
