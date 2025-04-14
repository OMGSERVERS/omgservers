package com.omgservers.service.operation.alias;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.shard.alias.DeleteAliasRequest;
import com.omgservers.schema.shard.alias.DeleteAliasResponse;
import com.omgservers.schema.shard.alias.ViewAliasesRequest;
import com.omgservers.schema.shard.alias.ViewAliasesResponse;
import com.omgservers.service.exception.ServerSideClientException;
import com.omgservers.service.shard.alias.AliasShard;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeleteAliasesByEntityIdOperationImpl implements DeleteAliasesByEntityIdOperation {

    final AliasShard aliasShard;

    @Override
    public Uni<Void> execute(final Long shardKey, final Long entityId) {
        return viewAliases(shardKey, entityId)
                .flatMap(aliases -> Multi.createFrom().iterable(aliases)
                        .onItem().transformToUniAndConcatenate(alias ->
                                deleteAlias(alias.getShardKey(), alias.getId())
                                        .onFailure(ServerSideClientException.class)
                                        .recoverWithItem(t -> {
                                            log.warn("Failed to delete alias, " +
                                                            "shardKey={}, " +
                                                            "entityId={}" +
                                                            "{}:{}",
                                                    alias.getShardKey(),
                                                    alias.getId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        })
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    Uni<List<AliasModel>> viewAliases(final Long shardKey,
                                      final Long entityId) {
        final var request = new ViewAliasesRequest();
        request.setShardKey(shardKey);
        request.setEntityId(entityId);
        return aliasShard.getService().execute(request)
                .map(ViewAliasesResponse::getAliases);
    }

    Uni<Boolean> deleteAlias(final Long shardKey, final Long id) {
        final var request = new DeleteAliasRequest(shardKey, id);
        return aliasShard.getService().execute(request)
                .map(DeleteAliasResponse::getDeleted);
    }
}
