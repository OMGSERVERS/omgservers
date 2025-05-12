package com.omgservers.service.operation.alias;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.model.alias.AliasQualifierEnum;
import com.omgservers.schema.shard.alias.SyncAliasRequest;
import com.omgservers.schema.shard.alias.SyncAliasResponse;
import com.omgservers.service.factory.alias.AliasModelFactory;
import com.omgservers.service.shard.alias.AliasShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreatePoolAliasOperationImpl implements CreatePoolAliasOperation {

    final AliasShard aliasShard;

    final AliasModelFactory aliasModelFactory;

    @Override
    public Uni<AliasModel> execute(final Long poolId,
                                   final String aliasValue) {
        final var alias = aliasModelFactory.create(AliasQualifierEnum.POOL,
                aliasValue,
                0L,
                poolId,
                aliasValue);
        final var request = new SyncAliasRequest(alias);
        return aliasShard.getService().execute(request)
                .map(SyncAliasResponse::getCreated)
                .invoke(created -> {
                    if (created) {
                        log.info("Created alias \"{}\" for the pool \"{}\"",
                                aliasValue, poolId);
                    }
                })
                .replaceWith(alias);
    }
}
