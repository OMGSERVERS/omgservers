package com.omgservers.service.module.alias.impl.service.aliasService.impl.method;

import com.omgservers.schema.module.alias.SyncAliasRequest;
import com.omgservers.schema.module.alias.SyncAliasResponse;
import com.omgservers.service.module.alias.impl.operation.alias.UpsertAliasOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncAliasMethodImpl implements SyncAliasMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertAliasOperation upsertAliasOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<SyncAliasResponse> execute(final SyncAliasRequest request) {
        log.trace("{}", request);

        final var alias = request.getAlias();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> upsertAliasOperation.execute(
                                            changeContext,
                                            sqlConnection,
                                            shard,
                                            alias))
                            .map(ChangeContext::getResult);
                })
                .map(SyncAliasResponse::new);
    }
}
