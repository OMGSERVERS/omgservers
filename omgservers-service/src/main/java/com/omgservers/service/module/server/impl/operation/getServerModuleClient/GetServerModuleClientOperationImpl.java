package com.omgservers.service.module.server.impl.operation.getServerModuleClient;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
class GetServerModuleClientOperationImpl implements GetServerModuleClientOperation {

    final Map<URI, ServerModuleClient> cache;

    GetServerModuleClientOperationImpl() {
        cache = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized ServerModuleClient getClient(final URI uri) {
        if (!cache.containsKey(uri)) {
            ServerModuleClient restClient = RestClientBuilder.newBuilder()
                    .baseUri(uri)
                    .build(ServerModuleClient.class);

            log.info("Module client was created, uri={}", uri);

            cache.put(uri, restClient);
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
