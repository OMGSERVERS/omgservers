package com.omgservers.test.operations.getAdminClientOperation;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
class GetAdminClientOperationImpl implements GetAdminClientOperation {

    final Map<URI, AdminClientForAdminAccount> cache;

    GetAdminClientOperationImpl() {
        cache = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized AdminClientForAdminAccount getAdminClientForAdminAccount(URI uri) {
        if (uri == null) {
            throw new IllegalArgumentException("uri is null");
        }

        if (!cache.containsKey(uri)) {
            var client = RestClientBuilder.newBuilder()
                    .baseUri(uri)
                    .build(AdminClientForAdminAccount.class);
            cache.put(uri, client);
            log.info("Client was created and cached, uri={}", uri);
        }

        return cache.get(uri);
    }
}
