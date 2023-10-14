package com.omgservers.module.matchmaker.impl.operation.getMatchmakerModuleClient;

import com.omgservers.exception.ServerSideBadRequestException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
class GetMatchmakerModuleClientOperationImpl implements GetMatchmakerModuleClientOperation {

    final Map<URI, MatchmakerModuleClient> cache;

    GetMatchmakerModuleClientOperationImpl() {
        cache = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized MatchmakerModuleClient getClient(final URI uri) {
        if (uri == null) {
            throw new ServerSideBadRequestException("uri is null");
        }

        if (!cache.containsKey(uri)) {
            final var client = RestClientBuilder.newBuilder()
                    .baseUri(uri)
                    .build(MatchmakerModuleClient.class);
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
