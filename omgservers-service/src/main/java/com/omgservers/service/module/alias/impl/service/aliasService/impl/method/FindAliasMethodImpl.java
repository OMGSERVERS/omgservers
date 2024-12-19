package com.omgservers.service.module.alias.impl.service.aliasService.impl.method;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.alias.FindAliasRequest;
import com.omgservers.schema.module.alias.FindAliasResponse;
import com.omgservers.service.exception.ServerSideInternalException;
import com.omgservers.service.module.alias.impl.operation.alias.SelectAliasByEntityIdOperation;
import com.omgservers.service.module.alias.impl.operation.alias.SelectAliasByValueOperation;
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
class FindAliasMethodImpl implements FindAliasMethod {

    final SelectAliasByEntityIdOperation selectAliasByEntityIdOperation;
    final SelectAliasByValueOperation selectAliasByValueOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<FindAliasResponse> execute(final FindAliasRequest request) {
        log.trace("Requested, {}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var shardKey = request.getShardKey();

                    return pgPool.withTransaction(
                            sqlConnection -> {
                                final var entityId = request.getEntityId();
                                final var value = request.getValue();
                                if (Objects.nonNull(entityId)) {
                                    return selectAliasByEntityIdOperation.execute(sqlConnection,
                                            shard.shard(),
                                            shardKey,
                                            entityId);
                                } else if (Objects.nonNull(value)) {
                                    return selectAliasByValueOperation.execute(sqlConnection,
                                            shard.shard(),
                                            shardKey,
                                            value);
                                } else {
                                    throw new ServerSideInternalException(
                                            ExceptionQualifierEnum.INTERNAL_EXCEPTION_OCCURRED,
                                            "entityId or value must be set to find alias");
                                }
                            });
                })
                .map(FindAliasResponse::new);
    }
}
