package com.omgservers.service.server.cache.impl.method;

import com.omgservers.service.server.cache.dto.GetClientLastActivityRequest;
import com.omgservers.service.server.cache.dto.GetClientLastActivityResponse;
import com.omgservers.service.server.cache.impl.operation.ExecuteCacheCommandOperation;
import com.omgservers.service.server.cache.impl.operation.GetClientLastActivityCacheKeyOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetClientLastActivityMethodImpl implements GetClientLastActivityMethod {

    final GetClientLastActivityCacheKeyOperation getClientLastActivityCacheKeyOperation;
    final ExecuteCacheCommandOperation executeCacheCommandOperation;

    @Override
    public Uni<GetClientLastActivityResponse> execute(final GetClientLastActivityRequest request) {
        final var clientId = request.getClientId();
        final var cacheKey = getClientLastActivityCacheKeyOperation.execute(clientId);
        return executeCacheCommandOperation.getInstant(cacheKey)
                .map(GetClientLastActivityResponse::new);
    }
}
