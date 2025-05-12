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
class FindRootAliasOperationImpl implements FindRootAliasOperation {

    final AliasShard aliasShard;

    @Override
    public Uni<AliasModel> execute() {
        final var request = new FindAliasRequest(AliasQualifierEnum.ROOT,
                DefaultAliasConfiguration.ROOT_ENTITY_ALIAS,
                0L,
                DefaultAliasConfiguration.ROOT_ENTITY_ALIAS);
        return aliasShard.getService().execute(request)
                .map(FindAliasResponse::getAlias);
    }
}
