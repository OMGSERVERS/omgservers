package com.omgservers.service.shard.alias.impl.service.aliasService.impl.method;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.alias.SyncAliasRequest;
import com.omgservers.schema.shard.alias.SyncAliasResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.alias.impl.operation.alias.UpsertAliasOperation;
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

    @Override
    public Uni<SyncAliasResponse> execute(final ShardModel shardModel,
                                          final SyncAliasRequest request) {
        log.trace("{}", request);

        final var alias = request.getAlias();
        return changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) -> upsertAliasOperation.execute(
                                changeContext,
                                sqlConnection,
                                shardModel.shard(),
                                alias))
                .map(ChangeContext::getResult)
                .map(SyncAliasResponse::new);
    }
}
