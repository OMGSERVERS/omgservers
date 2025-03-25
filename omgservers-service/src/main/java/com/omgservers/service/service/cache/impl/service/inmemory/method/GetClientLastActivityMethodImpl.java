package com.omgservers.service.service.cache.impl.service.inmemory.method;

import com.omgservers.service.service.cache.dto.GetClientLastActivityResponse;
import com.omgservers.service.service.cache.dto.GetClientLastActivityRequest;
import com.omgservers.service.service.cache.impl.operation.GetClientLastActivityCacheKeyOperation;
import com.omgservers.service.service.cache.impl.service.inmemory.component.InMemoryCache;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetClientLastActivityMethodImpl implements GetClientLastActivityMethod {

    final GetClientLastActivityCacheKeyOperation getClientLastActivityCacheKeyOperation;
    final InMemoryCache inMemoryCache;

    @Override
    public Uni<GetClientLastActivityResponse> execute(final GetClientLastActivityRequest request) {
        final var clientId = request.getClientId();
        final var cacheKey = getClientLastActivityCacheKeyOperation.execute(clientId);
        return Uni.createFrom().item(inMemoryCache.getInstant(cacheKey))
                .map(GetClientLastActivityResponse::new);
    }
}
