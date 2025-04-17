package com.omgservers.service.server.cache.impl.method;

import com.omgservers.service.configuration.CacheKeyQualifierEnum;
import com.omgservers.service.server.cache.dto.CacheClientLastActivityRequest;
import com.omgservers.service.server.cache.dto.CacheClientLastActivityResponse;
import com.omgservers.service.server.cache.impl.operation.ExecuteCacheCommandOperation;
import com.omgservers.service.server.cache.impl.operation.GetClientLastActivityCacheKeyOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CacheClientLastActivityMethodImpl implements CacheClientLastActivityMethod {

    final GetClientLastActivityCacheKeyOperation getClientLastActivityCacheKeyOperation;
    final ExecuteCacheCommandOperation executeCacheCommandOperation;

    @Override
    public Uni<CacheClientLastActivityResponse> execute(final CacheClientLastActivityRequest request) {
        final var clientId = request.getClientId();
        final var cacheKey = getClientLastActivityCacheKeyOperation.execute(clientId);
        final var cacheValue = request.getLastActivity();
        final var timeout = CacheKeyQualifierEnum.CLIENT_LAST_ACTIVITY.getTimeoutInSeconds();

        return executeCacheCommandOperation.put(cacheKey, cacheValue, timeout)
                .replaceWith(new CacheClientLastActivityResponse());
    }
}
