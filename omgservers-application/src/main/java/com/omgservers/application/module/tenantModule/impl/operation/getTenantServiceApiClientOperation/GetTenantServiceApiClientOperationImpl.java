package com.omgservers.application.module.tenantModule.impl.operation.getTenantServiceApiClientOperation;

import com.omgservers.application.exception.ServerSideBadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import jakarta.enterprise.context.ApplicationScoped;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
class GetTenantServiceApiClientOperationImpl implements GetTenantServiceApiClientOperation {

    final Map<URI, TenantServiceApiClient> cache;

    GetTenantServiceApiClientOperationImpl() {
        cache = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized TenantServiceApiClient getClient(final URI uri) {
        if (uri == null) {
            throw new ServerSideBadRequestException("uri is null");
        }

        if (!cache.containsKey(uri)) {
            TenantServiceApiClient restClient = RestClientBuilder.newBuilder()
                    .baseUri(uri)
                    .build(TenantServiceApiClient.class);
            cache.put(uri, restClient);
            log.info("Internal client was created and cached, uri={}", uri);
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
