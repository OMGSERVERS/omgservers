package com.omgservers.application.module.matchmakerModule.impl.operation.getMatchmakerServiceApiClientOperation;

import com.omgservers.application.exception.ServerSideBadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import jakarta.enterprise.context.ApplicationScoped;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
class GetMatchmakerServiceApiClientOperationImpl implements GetMatchmakerServiceApiClientOperation {

    final Map<URI, MatchmakerServiceApiClient> cache;

    GetMatchmakerServiceApiClientOperationImpl() {
        cache = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized MatchmakerServiceApiClient getClient(final URI uri) {
        if (uri == null) {
            throw new ServerSideBadRequestException("uri is null");
        }

        if (!cache.containsKey(uri)) {
            final var client = RestClientBuilder.newBuilder()
                    .baseUri(uri)
                    .build(MatchmakerServiceApiClient.class);
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
