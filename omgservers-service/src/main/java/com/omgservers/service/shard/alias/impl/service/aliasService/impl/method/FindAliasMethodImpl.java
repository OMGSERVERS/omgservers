package com.omgservers.service.shard.alias.impl.service.aliasService.impl.method;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.alias.FindAliasRequest;
import com.omgservers.schema.shard.alias.FindAliasResponse;
import com.omgservers.service.shard.alias.impl.operation.alias.SelectAliasByValueOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindAliasMethodImpl implements FindAliasMethod {

    final SelectAliasByValueOperation selectAliasByValueOperation;

    final PgPool pgPool;

    @Override
    public Uni<FindAliasResponse> execute(final ShardModel shardModel,
                                          final FindAliasRequest request) {
        log.trace("{}", request);

        return pgPool.withTransaction(sqlConnection -> {
                    final var shardKey = request.getShardKey();
                    final var uniquenessGroup = request.getUniquenessGroup();
                    final var value = request.getValue();
                    return selectAliasByValueOperation.execute(sqlConnection,
                            shardModel.slot(),
                            shardKey,
                            uniquenessGroup,
                            value);
                })
                .map(FindAliasResponse::new);
    }
}
