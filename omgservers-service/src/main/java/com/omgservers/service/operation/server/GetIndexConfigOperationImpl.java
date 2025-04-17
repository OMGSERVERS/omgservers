package com.omgservers.service.operation.server;

import com.omgservers.schema.master.index.GetIndexRequest;
import com.omgservers.schema.master.index.GetIndexResponse;
import com.omgservers.schema.model.index.IndexConfigDto;
import com.omgservers.schema.model.index.IndexModel;
import com.omgservers.service.master.index.IndexMaster;
import com.omgservers.service.server.cache.CacheService;
import com.omgservers.service.server.cache.dto.GetCachedIndexConfigRequest;
import com.omgservers.service.server.cache.dto.GetCachedIndexConfigResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class GetIndexConfigOperationImpl implements GetIndexConfigOperation {

    final IndexMaster indexMaster;

    final CacheService cacheService;

    @Override
    public Uni<IndexConfigDto> execute() {
        return getCachedIndexConfig()
                .flatMap(cachedIndexConfig -> {
                    if (Objects.nonNull(cachedIndexConfig)) {
                        return Uni.createFrom().item(cachedIndexConfig);
                    } else {
                        return getIndex()
                                .map(IndexModel::getConfig);
                    }
                });
    }

    Uni<IndexConfigDto> getCachedIndexConfig() {
        final var request = new GetCachedIndexConfigRequest();
        return cacheService.execute(request)
                .map(GetCachedIndexConfigResponse::getIndexConfig);
    }

    Uni<IndexModel> getIndex() {
        final var request = new GetIndexRequest();
        return indexMaster.getService().execute(request)
                .map(GetIndexResponse::getIndex);
    }
}
