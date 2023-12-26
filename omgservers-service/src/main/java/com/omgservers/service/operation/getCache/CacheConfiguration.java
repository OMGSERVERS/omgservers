package com.omgservers.service.operation.getCache;

import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CacheConfiguration {

    static final String WRITE_BASED_CACHE = "write-based";
    static final String ACCESS_BASED_CACHE = "access-based";

    @CacheResult(cacheName = WRITE_BASED_CACHE)
    String configureWriteBasedCache() {
        return "configured";
    }

    @CacheResult(cacheName = ACCESS_BASED_CACHE)
    String configuredAccessBasedCache() {
        return "configured";
    }
}
