package com.omgservers.connector.operation;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public abstract class GetRestClientOperationImpl<T> {

    final Class<T> clazz;
    final Map<URI, T> cache;

    public GetRestClientOperationImpl(Class<T> clazz) {
        this.clazz = clazz;
        cache = new ConcurrentHashMap<>();
    }

    public synchronized T execute(final URI uri) {
        if (!cache.containsKey(uri)) {
            final var client = RestClientBuilder.newBuilder()
                    .baseUri(uri)
                    .build(clazz);

            log.debug("Rest client \"{}\" to \"{}\" created", clazz, uri);

            cache.put(uri, client);
        }

        return cache.get(uri);
    }
}
