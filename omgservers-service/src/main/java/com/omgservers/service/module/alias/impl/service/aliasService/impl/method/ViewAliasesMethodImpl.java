package com.omgservers.service.module.alias.impl.service.aliasService.impl.method;

import com.omgservers.schema.module.alias.ViewAliasesRequest;
import com.omgservers.schema.module.alias.ViewAliasesResponse;
import com.omgservers.service.module.alias.impl.operation.alias.SelectAliasesByEntityIdOperation;
import com.omgservers.service.module.alias.impl.operation.alias.SelectAliasesByShardKeyOperation;
import com.omgservers.service.module.alias.impl.operation.alias.SelectAliasesByUniquenessGroupOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewAliasesMethodImpl implements ViewAliasesMethod {

    final SelectAliasesByUniquenessGroupOperation selectAliasesByUniquenessGroupOperation;
    final SelectAliasesByShardKeyOperation selectAliasesByShardKeyOperation;
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
                                final var uniquenessGroup = request.getUniquenessGroup();
                                final var entityId = request.getEntityId();
                                if (Objects.nonNull(entityId)) {
                                    return selectAliasesByEntityIdOperation.execute(sqlConnection,
                                            shard.shard(),
                                            shardKey,
                                            entityId);
                                } else if (Objects.nonNull(uniquenessGroup)) {
                                    return selectAliasesByUniquenessGroupOperation.execute(sqlConnection,
                                            shard.shard(),
                                            shardKey,
                                            uniquenessGroup);
                                } else {
                                    return selectAliasesByShardKeyOperation.execute(sqlConnection,
                                            shard.shard(),
                                            shardKey);
                                }
                            });
                })
                .map(ViewAliasesResponse::new);
    }
}
