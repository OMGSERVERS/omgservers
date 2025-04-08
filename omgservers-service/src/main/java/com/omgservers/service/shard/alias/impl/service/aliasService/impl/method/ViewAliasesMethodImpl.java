package com.omgservers.service.shard.alias.impl.service.aliasService.impl.method;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.alias.ViewAliasesRequest;
import com.omgservers.schema.module.alias.ViewAliasesResponse;
import com.omgservers.service.operation.server.CheckShardOperation;
import com.omgservers.service.shard.alias.impl.operation.alias.SelectActiveAliasesByEntityIdOperation;
import com.omgservers.service.shard.alias.impl.operation.alias.SelectActiveAliasesByShardKeyOperation;
import com.omgservers.service.shard.alias.impl.operation.alias.SelectActiveAliasesByUniquenessGroupOperation;
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

    final SelectActiveAliasesByUniquenessGroupOperation selectActiveAliasesByUniquenessGroupOperation;
    final SelectActiveAliasesByShardKeyOperation selectActiveAliasesByShardKeyOperation;
    final SelectActiveAliasesByEntityIdOperation selectActiveAliasesByEntityIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewAliasesResponse> execute(final ShardModel shardModel,
                                            final ViewAliasesRequest request) {
        log.trace("{}", request);

        final var shardKey = request.getShardKey();
        return pgPool.withTransaction(
                        sqlConnection -> {
                            final var uniquenessGroup = request.getUniquenessGroup();
                            final var entityId = request.getEntityId();
                            if (Objects.nonNull(entityId)) {
                                return selectActiveAliasesByEntityIdOperation.execute(sqlConnection,
                                        shardModel.shard(),
                                        shardKey,
                                        entityId);
                            } else if (Objects.nonNull(uniquenessGroup)) {
                                return selectActiveAliasesByUniquenessGroupOperation.execute(sqlConnection,
                                        shardModel.shard(),
                                        shardKey,
                                        uniquenessGroup);
                            } else {
                                return selectActiveAliasesByShardKeyOperation.execute(sqlConnection,
                                        shardModel.shard(),
                                        shardKey);
                            }
                        })
                .map(ViewAliasesResponse::new);
    }
}
