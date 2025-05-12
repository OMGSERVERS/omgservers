package com.omgservers.service.operation.alias;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.model.alias.AliasQualifierEnum;
import com.omgservers.schema.shard.alias.DeleteAliasRequest;
import com.omgservers.schema.shard.alias.DeleteAliasResponse;
import com.omgservers.schema.shard.alias.FindAliasRequest;
import com.omgservers.schema.shard.alias.FindAliasResponse;
import com.omgservers.service.factory.alias.AliasModelFactory;
import com.omgservers.service.shard.alias.AliasShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeletePtrAliasOperationImpl implements DeletePtrAliasOperation {

    final AliasShard aliasShard;

    final AliasModelFactory aliasModelFactory;

    @Override
    public Uni<Boolean> execute(final Long entityId,
                                final String aliasValue) {
        return findAlias(entityId, aliasValue)
                .flatMap(alias -> {
                    final var shardKey = alias.getShardKey();
                    final var aliasId = alias.getId();
                    return deleteAlias(shardKey, aliasId);
                })
                .invoke(deleted -> {
                    if (deleted) {
                        log.info("Deleted PTR alias \"{}\" for the entity \"{}\"", aliasValue, entityId);
                    }
                });
    }

    Uni<AliasModel> findAlias(final Long entityId, final String alisValue) {
        final var shardKey = entityId.toString();
        final var request = new FindAliasRequest(AliasQualifierEnum.PTR, shardKey, entityId, alisValue);
        return aliasShard.getService().execute(request)
                .map(FindAliasResponse::getAlias);
    }

    Uni<Boolean> deleteAlias(final String shardKey, final Long aliasId) {
        final var request = new DeleteAliasRequest(shardKey, aliasId);
        return aliasShard.getService().execute(request)
                .map(DeleteAliasResponse::getDeleted);
    }
}
