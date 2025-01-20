package com.omgservers.service.shard.alias.impl.service.aliasService.impl.method;

import com.omgservers.schema.module.alias.FindAliasRequest;
import com.omgservers.schema.module.alias.FindAliasResponse;
import com.omgservers.service.shard.alias.impl.operation.alias.SelectAliasByValueOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
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
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<FindAliasResponse> execute(final FindAliasRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> pgPool.withTransaction(
                        sqlConnection -> {
                            final var shardKey = request.getShardKey();
                            final var uniquenessGroup = request.getUniquenessGroup();
                            final var value = request.getValue();
                            return selectAliasByValueOperation.execute(sqlConnection,
                                    shard.shard(),
                                    shardKey,
                                    uniquenessGroup,
                                    value);
                        }))
                .map(FindAliasResponse::new);
    }
}
