package com.omgservers.service.server.cache.impl.method;

import com.omgservers.service.server.cache.dto.GetRuntimeLastActivityRequest;
import com.omgservers.service.server.cache.dto.GetRuntimeLastActivityResponse;
import com.omgservers.service.server.cache.impl.operation.ExecuteCacheCommandOperation;
import com.omgservers.service.server.cache.impl.operation.GetRuntimeLastActivityCacheKeyOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetRuntimeLastActivityMethodImpl implements GetRuntimeLastActivityMethod {

    final GetRuntimeLastActivityCacheKeyOperation getRuntimeLastActivityCacheKeyOperation;
    final ExecuteCacheCommandOperation executeCacheCommandOperation;

    @Override
    public Uni<GetRuntimeLastActivityResponse> execute(final GetRuntimeLastActivityRequest request) {
        final var runtimeId = request.getRuntimeId();
        final var cacheKey = getRuntimeLastActivityCacheKeyOperation.execute(runtimeId);
        return executeCacheCommandOperation.getInstant(cacheKey)
                .map(GetRuntimeLastActivityResponse::new);
    }
}
