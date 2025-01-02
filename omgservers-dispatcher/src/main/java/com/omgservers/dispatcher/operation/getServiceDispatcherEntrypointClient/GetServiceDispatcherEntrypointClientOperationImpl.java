package com.omgservers.dispatcher.operation.getServiceDispatcherEntrypointClient;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
class GetServiceDispatcherEntrypointClientOperationImpl implements GetServiceDispatcherEntrypointClientOperation {

    final Map<URI, ServiceDispatcherEntrypointModuleClient> cache;

    GetServiceDispatcherEntrypointClientOperationImpl() {
        cache = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized ServiceDispatcherEntrypointModuleClient getClient(final URI uri) {
        if (!cache.containsKey(uri)) {
            final var client = RestClientBuilder.newBuilder()
                    .baseUri(uri)
                    .build(ServiceDispatcherEntrypointModuleClient.class);

            log.debug("Module client was created, uri={}", uri);

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
