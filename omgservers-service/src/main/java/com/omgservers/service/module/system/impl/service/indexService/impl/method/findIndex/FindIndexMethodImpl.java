package com.omgservers.service.module.system.impl.service.indexService.impl.method.findIndex;

import com.omgservers.model.dto.system.FindIndexRequest;
import com.omgservers.model.dto.system.FindIndexResponse;
import com.omgservers.model.index.IndexModel;
import com.omgservers.service.module.system.impl.operation.selectIndexByName.SelectIndexByNameOperation;
import com.omgservers.service.operation.getCache.GetCacheOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindIndexMethodImpl implements FindIndexMethod {

    final SelectIndexByNameOperation selectIndexByNameOperation;
    final GetCacheOperation getCacheOperation;

    final PgPool pgPool;

    @Override
    public Uni<FindIndexResponse> findIndex(final FindIndexRequest request) {
        log.debug("Find index, request={}", request);

        final var name = request.getName();
        return getCacheOperation.useWriteBasedCache(name, k -> selectIndex(name), IndexModel.class)
                .map(FindIndexResponse::new);
    }

    Uni<IndexModel> selectIndex(final String name) {
        return pgPool.withTransaction(sqlConnection -> selectIndexByNameOperation
                .selectIndexByName(sqlConnection, name));
    }
}
