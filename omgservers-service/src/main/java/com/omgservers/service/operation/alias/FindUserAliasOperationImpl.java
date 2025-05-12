package com.omgservers.service.operation.alias;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.model.alias.AliasQualifierEnum;
import com.omgservers.schema.shard.alias.FindAliasRequest;
import com.omgservers.schema.shard.alias.FindAliasResponse;
import com.omgservers.service.shard.alias.AliasShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindUserAliasOperationImpl implements FindUserAliasOperation {

    final AliasShard aliasShard;

    @Override
    public Uni<AliasModel> execute(String aliasValue) {
        final var request = new FindAliasRequest(AliasQualifierEnum.USER,
                aliasValue,
                0L,
                aliasValue);
        return aliasShard.getService().execute(request)
                .map(FindAliasResponse::getAlias);
    }
}
