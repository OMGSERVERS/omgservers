package com.omgservers.service.server.cache.impl;

import com.omgservers.service.server.cache.CacheService;
import com.omgservers.service.server.cache.dto.CacheClientLastActivityRequest;
import com.omgservers.service.server.cache.dto.CacheClientLastActivityResponse;
import com.omgservers.service.server.cache.dto.CacheIndexConfigRequest;
import com.omgservers.service.server.cache.dto.CacheIndexConfigResponse;
import com.omgservers.service.server.cache.dto.CacheRuntimeLastActivityRequest;
import com.omgservers.service.server.cache.dto.CacheRuntimeLastActivityResponse;
import com.omgservers.service.server.cache.dto.GetCachedClientLastActivityRequest;
import com.omgservers.service.server.cache.dto.GetCachedClientLastActivityResponse;
import com.omgservers.service.server.cache.dto.GetCachedClientsLastActivitiesRequest;
import com.omgservers.service.server.cache.dto.GetCachedClientsLastActivitiesResponse;
import com.omgservers.service.server.cache.dto.GetCachedIndexConfigRequest;
import com.omgservers.service.server.cache.dto.GetCachedIndexConfigResponse;
import com.omgservers.service.server.cache.dto.GetCachedRuntimeLastActivityRequest;
import com.omgservers.service.server.cache.dto.GetCachedRuntimeLastActivityResponse;
import com.omgservers.service.server.cache.impl.method.CacheClientLastActivityMethod;
import com.omgservers.service.server.cache.impl.method.CacheIndexConfigMethod;
import com.omgservers.service.server.cache.impl.method.CacheRuntimeLastActivityMethod;
import com.omgservers.service.server.cache.impl.method.GetCachedClientLastActivityMethod;
import com.omgservers.service.server.cache.impl.method.GetCachedClientsLastActivitiesMethod;
import com.omgservers.service.server.cache.impl.method.GetCachedIndexConfigMethod;
import com.omgservers.service.server.cache.impl.method.GetCachedRuntimeLastActivityMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CacheServiceImpl implements CacheService {

    final GetCachedClientsLastActivitiesMethod getCachedClientsLastActivitiesMethod;
    final GetCachedRuntimeLastActivityMethod getCachedRuntimeLastActivityMethod;
    final GetCachedClientLastActivityMethod getCachedClientLastActivityMethod;
    final CacheRuntimeLastActivityMethod cacheRuntimeLastActivityMethod;
    final CacheClientLastActivityMethod cacheClientLastActivityMethod;
    final GetCachedIndexConfigMethod getCachedIndexConfigMethod;
    final CacheIndexConfigMethod cacheIndexConfigMethod;

    @Override
    public Uni<GetCachedRuntimeLastActivityResponse> execute(@Valid final GetCachedRuntimeLastActivityRequest request) {
        return getCachedRuntimeLastActivityMethod.execute(request);
    }

    @Override
    public Uni<CacheRuntimeLastActivityResponse> execute(@Valid final CacheRuntimeLastActivityRequest request) {
        return cacheRuntimeLastActivityMethod.execute(request);
    }

    @Override
    public Uni<GetCachedClientLastActivityResponse> execute(@Valid final GetCachedClientLastActivityRequest request) {
        return getCachedClientLastActivityMethod.execute(request);
    }

    @Override
    public Uni<GetCachedClientsLastActivitiesResponse> execute(
            @Valid final GetCachedClientsLastActivitiesRequest request) {
        return getCachedClientsLastActivitiesMethod.execute(request);
    }

    @Override
    public Uni<CacheClientLastActivityResponse> execute(@Valid final CacheClientLastActivityRequest request) {
        return cacheClientLastActivityMethod.execute(request);
    }

    @Override
    public Uni<GetCachedIndexConfigResponse> execute(@Valid final GetCachedIndexConfigRequest request) {
        return getCachedIndexConfigMethod.execute(request);
    }

    @Override
    public Uni<CacheIndexConfigResponse> execute(@Valid final CacheIndexConfigRequest request) {
        return cacheIndexConfigMethod.execute(request);
    }
}
