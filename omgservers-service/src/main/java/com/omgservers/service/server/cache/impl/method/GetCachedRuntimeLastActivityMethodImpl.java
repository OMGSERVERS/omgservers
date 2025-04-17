package com.omgservers.service.server.cache.impl.method;

import com.omgservers.service.server.cache.dto.GetCachedRuntimeLastActivityRequest;
import com.omgservers.service.server.cache.dto.GetCachedRuntimeLastActivityResponse;
import com.omgservers.service.server.cache.impl.operation.ExecuteCacheCommandOperation;
import com.omgservers.service.server.cache.impl.operation.GetRuntimeLastActivityCacheKeyOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetCachedRuntimeLastActivityMethodImpl implements GetCachedRuntimeLastActivityMethod {

    final GetRuntimeLastActivityCacheKeyOperation getRuntimeLastActivityCacheKeyOperation;
    final ExecuteCacheCommandOperation executeCacheCommandOperation;

    @Override
    public Uni<GetCachedRuntimeLastActivityResponse> execute(final GetCachedRuntimeLastActivityRequest request) {
        final var runtimeId = request.getRuntimeId();
        final var cacheKey = getRuntimeLastActivityCacheKeyOperation.execute(runtimeId);
        return executeCacheCommandOperation.getInstant(cacheKey)
                .map(GetCachedRuntimeLastActivityResponse::new);
    }
}
