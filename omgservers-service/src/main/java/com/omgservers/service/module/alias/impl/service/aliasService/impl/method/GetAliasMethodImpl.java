package com.omgservers.service.module.alias.impl.service.aliasService.impl.method;

import com.omgservers.schema.module.alias.GetAliasRequest;
import com.omgservers.schema.module.alias.GetAliasResponse;
import com.omgservers.service.module.alias.impl.operation.alias.SelectAliasOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetAliasMethodImpl implements GetAliasMethod {

    final SelectAliasOperation selectAliasOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetAliasResponse> execute(final GetAliasRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectAliasOperation
                            .execute(sqlConnection, shard.shard(), id));
                })
                .map(GetAliasResponse::new);
    }
}
