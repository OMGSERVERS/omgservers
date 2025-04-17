package com.omgservers.service.server.cache.impl.method;

import com.omgservers.schema.model.index.IndexConfigDto;
import com.omgservers.service.configuration.CacheKeyQualifierEnum;
import com.omgservers.service.server.cache.dto.GetCachedIndexConfigRequest;
import com.omgservers.service.server.cache.dto.GetCachedIndexConfigResponse;
import com.omgservers.service.server.cache.impl.operation.ExecuteCacheCommandOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetCachedIndexConfigMethodImpl implements GetCachedIndexConfigMethod {

    final ExecuteCacheCommandOperation executeCacheCommandOperation;

    @Override
    public Uni<GetCachedIndexConfigResponse> execute(final GetCachedIndexConfigRequest request) {
        final var cacheKey = CacheKeyQualifierEnum.INDEX_CONFIG.getKey();
        return executeCacheCommandOperation.get(cacheKey, IndexConfigDto.class)
                .map(GetCachedIndexConfigResponse::new);
    }
}
