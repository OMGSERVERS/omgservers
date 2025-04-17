package com.omgservers.service.server.cache.impl.method;

import com.omgservers.service.server.cache.dto.GetCachedClientLastActivityRequest;
import com.omgservers.service.server.cache.dto.GetCachedClientLastActivityResponse;
import com.omgservers.service.server.cache.impl.operation.ExecuteCacheCommandOperation;
import com.omgservers.service.server.cache.impl.operation.GetClientLastActivityCacheKeyOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetCachedClientLastActivityMethodImpl implements GetCachedClientLastActivityMethod {

    final GetClientLastActivityCacheKeyOperation getClientLastActivityCacheKeyOperation;
    final ExecuteCacheCommandOperation executeCacheCommandOperation;

    @Override
    public Uni<GetCachedClientLastActivityResponse> execute(final GetCachedClientLastActivityRequest request) {
        final var clientId = request.getClientId();
        final var cacheKey = getClientLastActivityCacheKeyOperation.execute(clientId);
        return executeCacheCommandOperation.getInstant(cacheKey)
                .map(GetCachedClientLastActivityResponse::new);
    }
}
