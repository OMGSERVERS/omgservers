package com.omgservers.service.operation.alias;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.model.alias.AliasQualifierEnum;
import com.omgservers.schema.shard.alias.FindAliasRequest;
import com.omgservers.schema.shard.alias.FindAliasResponse;
import com.omgservers.service.configuration.DefaultAliasConfiguration;
import com.omgservers.service.shard.alias.AliasShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetDefaultPoolIdOperationImpl implements GetDefaultPoolIdOperation {

    final AliasShard aliasShard;

    @Override
    public Uni<Long> execute() {
        final var request = new FindAliasRequest(AliasQualifierEnum.POOL,
                DefaultAliasConfiguration.DEFAULT_POOL_ALIAS,
                0L,
                DefaultAliasConfiguration.DEFAULT_POOL_ALIAS);
        return aliasShard.getService().execute(request)
                .map(FindAliasResponse::getAlias)
                .map(AliasModel::getEntityId);
    }
}
