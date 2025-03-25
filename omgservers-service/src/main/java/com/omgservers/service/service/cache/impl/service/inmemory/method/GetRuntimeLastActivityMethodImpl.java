package com.omgservers.service.service.cache.impl.service.inmemory.method;

import com.omgservers.service.service.cache.dto.GetRuntimeLastActivityRequest;
import com.omgservers.service.service.cache.dto.GetRuntimeLastActivityResponse;
import com.omgservers.service.service.cache.impl.operation.GetRuntimeLastActivityCacheKeyOperation;
import com.omgservers.service.service.cache.impl.service.inmemory.component.InMemoryCache;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetRuntimeLastActivityMethodImpl implements GetRuntimeLastActivityMethod {

    final GetRuntimeLastActivityCacheKeyOperation getRuntimeLastActivityCacheKeyOperation;
    final InMemoryCache inMemoryCache;

    @Override
    public Uni<GetRuntimeLastActivityResponse> execute(final GetRuntimeLastActivityRequest request) {
        final var runtimeId = request.getRuntimeId();
        final var cacheKey = getRuntimeLastActivityCacheKeyOperation.execute(runtimeId);
        return Uni.createFrom().item(inMemoryCache.getInstant(cacheKey))
                .map(GetRuntimeLastActivityResponse::new);
    }
}
