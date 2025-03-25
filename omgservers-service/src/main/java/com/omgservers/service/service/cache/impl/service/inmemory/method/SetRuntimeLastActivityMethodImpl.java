package com.omgservers.service.service.cache.impl.service.inmemory.method;

import com.omgservers.service.configuration.CacheKeyConfiguration;
import com.omgservers.service.service.cache.dto.SetRuntimeLastActivityRequest;
import com.omgservers.service.service.cache.dto.SetRuntimeLastActivityResponse;
import com.omgservers.service.service.cache.impl.operation.GetRuntimeLastActivityCacheKeyOperation;
import com.omgservers.service.service.cache.impl.service.inmemory.component.InMemoryCache;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SetRuntimeLastActivityMethodImpl implements SetRuntimeLastActivityMethod {

    final GetRuntimeLastActivityCacheKeyOperation getRuntimeLastActivityCacheKeyOperation;
    final InMemoryCache inMemoryCache;

    @Override
    public Uni<SetRuntimeLastActivityResponse> execute(final SetRuntimeLastActivityRequest request) {
        final var runtimeId = request.getRuntimeId();
        final var cacheKey = getRuntimeLastActivityCacheKeyOperation.execute(runtimeId);
        final var cacheValue = request.getLastActivity();
        final var expiration = Instant.now()
                .plusSeconds(CacheKeyConfiguration.RUNTIME_LAST_ACTIVITY_LIFETIME);

        inMemoryCache.put(cacheKey, cacheValue, expiration);

        return Uni.createFrom().item(new SetRuntimeLastActivityResponse());
    }
}
