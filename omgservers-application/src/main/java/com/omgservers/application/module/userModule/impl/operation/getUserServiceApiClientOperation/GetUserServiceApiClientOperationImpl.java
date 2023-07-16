package com.omgservers.application.module.userModule.impl.operation.getUserServiceApiClientOperation;

import com.omgservers.application.exception.ServerSideBadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import jakarta.enterprise.context.ApplicationScoped;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
class GetUserServiceApiClientOperationImpl implements GetUserServiceApiClientOperation {

    final Map<URI, UserServiceApiClient> cache;

    GetUserServiceApiClientOperationImpl() {
        cache = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized UserServiceApiClient getClient(final URI uri) {
        if (uri == null) {
            throw new ServerSideBadRequestException("uri is null");
        }

        if (!cache.containsKey(uri)) {
            final var client = RestClientBuilder.newBuilder()
                    .baseUri(uri)
                    .build(UserServiceApiClient.class);
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
