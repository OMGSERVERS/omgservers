package com.omgservers.service.module.alias.impl.service.aliasService.impl.method;

import com.omgservers.schema.module.alias.ViewAliasesRequest;
import com.omgservers.schema.module.alias.ViewAliasesResponse;
import com.omgservers.service.module.alias.impl.operation.alias.SelectAliasesByEntityIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewAliasesMethodImpl implements ViewAliasesMethod {

    final SelectAliasesByEntityIdOperation selectAliasesByEntityIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewAliasesResponse> execute(final ViewAliasesRequest request) {
        log.trace("Requested, {}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var shardKey = request.getShardKey();

                    return pgPool.withTransaction(
                            sqlConnection -> {
                                final var entityId = request.getEntityId();
                                return selectAliasesByEntityIdOperation.execute(sqlConnection,
                                        shard.shard(),
                                        shardKey,
                                        entityId);
                            });
                })
                .map(ViewAliasesResponse::new);
    }
}
