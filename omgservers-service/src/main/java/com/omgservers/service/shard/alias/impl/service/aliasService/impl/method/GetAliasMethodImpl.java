package com.omgservers.service.shard.alias.impl.service.aliasService.impl.method;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.alias.GetAliasRequest;
import com.omgservers.schema.module.alias.GetAliasResponse;
import com.omgservers.service.shard.alias.impl.operation.alias.SelectAliasOperation;
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

    final PgPool pgPool;

    @Override
    public Uni<GetAliasResponse> execute(final ShardModel shardModel,
                                         final GetAliasRequest request) {
        log.trace("{}", request);

        final var id = request.getId();
        return pgPool.withTransaction(sqlConnection -> selectAliasOperation
                        .execute(sqlConnection, shardModel.shard(), id))
                .map(GetAliasResponse::new);
    }
}
