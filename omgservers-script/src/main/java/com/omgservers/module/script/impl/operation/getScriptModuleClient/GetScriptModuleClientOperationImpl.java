package com.omgservers.module.script.impl.operation.getScriptModuleClient;

import com.omgservers.exception.ServerSideBadRequestException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
class GetScriptModuleClientOperationImpl implements GetScriptModuleClientOperation {

    final Map<URI, ScriptModuleClient> cache;

    GetScriptModuleClientOperationImpl() {
        cache = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized ScriptModuleClient getClient(final URI uri) {
        if (uri == null) {
            throw new ServerSideBadRequestException("uri is null");
        }

        if (!cache.containsKey(uri)) {
            ScriptModuleClient restClient = RestClientBuilder.newBuilder()
                    .baseUri(uri)
                    .build(ScriptModuleClient.class);
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
