package com.omgservers.service.server.cache.impl.method;

import com.omgservers.service.configuration.CacheKeyQualifierEnum;
import com.omgservers.service.server.cache.dto.CacheIndexConfigRequest;
import com.omgservers.service.server.cache.dto.CacheIndexConfigResponse;
import com.omgservers.service.server.cache.impl.operation.ExecuteCacheCommandOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CacheIndexConfigMethodImpl implements CacheIndexConfigMethod {

    final ExecuteCacheCommandOperation executeCacheCommandOperation;

    @Override
    public Uni<CacheIndexConfigResponse> execute(final CacheIndexConfigRequest request) {
        final var cacheKey = CacheKeyQualifierEnum.INDEX_CONFIG.getKey();
        final var cacheValue = request.getIndexConfig();
        final var timeout = CacheKeyQualifierEnum.INDEX_CONFIG.getTimeoutInSeconds();

        return executeCacheCommandOperation.put(cacheKey, cacheValue, timeout)
                .replaceWith(new CacheIndexConfigResponse());
    }
}
