package com.omgservers.service.server.cache.impl.method;

import com.omgservers.service.configuration.CacheKeyQualifierEnum;
import com.omgservers.service.server.cache.dto.SetRuntimeLastActivityRequest;
import com.omgservers.service.server.cache.dto.SetRuntimeLastActivityResponse;
import com.omgservers.service.server.cache.impl.operation.ExecuteCacheCommandOperation;
import com.omgservers.service.server.cache.impl.operation.GetRuntimeLastActivityCacheKeyOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SetRuntimeLastActivityMethodImpl implements SetRuntimeLastActivityMethod {

    final GetRuntimeLastActivityCacheKeyOperation getRuntimeLastActivityCacheKeyOperation;
    final ExecuteCacheCommandOperation executeCacheCommandOperation;

    @Override
    public Uni<SetRuntimeLastActivityResponse> execute(final SetRuntimeLastActivityRequest request) {
        final var runtimeId = request.getRuntimeId();
        final var cacheKey = getRuntimeLastActivityCacheKeyOperation.execute(runtimeId);
        final var cacheValue = request.getLastActivity();
        final var timeout = CacheKeyQualifierEnum.RUNTIME_LAST_ACTIVITY.getTimeoutInSeconds();

        return executeCacheCommandOperation.put(cacheKey, cacheValue, timeout)
                .replaceWith(new SetRuntimeLastActivityResponse());
    }
}
