package com.omgservers.application.module.versionModule.impl.operation.getVersionServiceApiClientOperation;

import com.omgservers.application.exception.ServerSideBadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import jakarta.enterprise.context.ApplicationScoped;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
class GetVersionServiceApiClientOperationImpl implements GetVersionServiceApiClientOperation {

    final Map<URI, VersionServiceApiClient> cache;

    GetVersionServiceApiClientOperationImpl() {
        cache = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized VersionServiceApiClient getClient(final URI uri) {
        if (uri == null) {
            throw new ServerSideBadRequestException("uri is null");
        }

        if (!cache.containsKey(uri)) {
            final var client = RestClientBuilder.newBuilder()
                    .baseUri(uri)
                    .build(VersionServiceApiClient.class);
            cache.put(uri, client);
            log.info("Client was created and cached, uri={}", uri);
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
