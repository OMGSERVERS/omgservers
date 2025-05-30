package com.omgservers.service.shard.alias.impl.service.aliasService.impl.method;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.alias.DeleteAliasRequest;
import com.omgservers.schema.shard.alias.DeleteAliasResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.alias.impl.operation.alias.DeleteAliasOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteAliasMethodImpl implements DeleteAliasMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteAliasOperation deleteAliasOperation;

    @Override
    public Uni<DeleteAliasResponse> execute(final ShardModel shardModel,
                                            final DeleteAliasRequest request) {
        log.debug("{}", request);

        final var shardKey = request.getShardKey();
        final var id = request.getId();
        return changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) -> deleteAliasOperation.execute(changeContext,
                                sqlConnection,
                                shardModel.slot(),
                                shardKey,
                                id))
                .map(ChangeContext::getResult)
                .map(DeleteAliasResponse::new);
    }
}
