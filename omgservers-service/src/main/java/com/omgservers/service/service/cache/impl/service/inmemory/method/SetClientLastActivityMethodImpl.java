package com.omgservers.service.service.cache.impl.service.inmemory.method;

import com.omgservers.service.configuration.CacheKeyConfiguration;
import com.omgservers.service.service.cache.dto.SetClientLastActivityRequest;
import com.omgservers.service.service.cache.dto.SetClientLastActivityResponse;
import com.omgservers.service.service.cache.impl.operation.GetClientLastActivityCacheKeyOperation;
import com.omgservers.service.service.cache.impl.service.inmemory.component.InMemoryCache;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SetClientLastActivityMethodImpl implements SetClientLastActivityMethod {

    final GetClientLastActivityCacheKeyOperation getClientLastActivityCacheKeyOperation;
    final InMemoryCache inMemoryCache;

    @Override
    public Uni<SetClientLastActivityResponse> execute(final SetClientLastActivityRequest request) {
        final var clientId = request.getClientId();
        final var cacheKey = getClientLastActivityCacheKeyOperation.execute(clientId);
        final var cacheValue = request.getLastActivity();
        final var expiration = Instant.now()
                .plusSeconds(CacheKeyConfiguration.CLIENT_LAST_ACTIVITY_LIFETIME);

        inMemoryCache.put(cacheKey, cacheValue, expiration);

        return Uni.createFrom().item(new SetClientLastActivityResponse());
    }
}
