package com.omgservers.service.service.jenkins.operation;

import io.quarkus.rest.client.reactive.QuarkusRestClientBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
class GetJenkinsClientOperationImpl implements GetJenkinsClientOperation {

    final Map<URI, JenkinsClient> cache;

    GetJenkinsClientOperationImpl() {
        cache = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized JenkinsClient getClient(final URI uri) {
        if (!cache.containsKey(uri)) {
            final var client = QuarkusRestClientBuilder.newBuilder()
                    .baseUri(uri)
                    .build(JenkinsClient.class);

            log.debug("Jenkins client to the \"{}\" was created", uri);

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
