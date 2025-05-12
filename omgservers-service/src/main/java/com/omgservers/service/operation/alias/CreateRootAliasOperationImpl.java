package com.omgservers.service.operation.alias;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.model.alias.AliasQualifierEnum;
import com.omgservers.schema.shard.alias.SyncAliasRequest;
import com.omgservers.schema.shard.alias.SyncAliasResponse;
import com.omgservers.service.configuration.DefaultAliasConfiguration;
import com.omgservers.service.factory.alias.AliasModelFactory;
import com.omgservers.service.shard.alias.AliasShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateRootAliasOperationImpl implements CreateRootAliasOperation {

    final AliasShard aliasShard;

    final AliasModelFactory aliasModelFactory;

    @Override
    public Uni<AliasModel> execute(final Long rootId) {
        final var aliasValue = DefaultAliasConfiguration.ROOT_ENTITY_ALIAS;
        final var alias = aliasModelFactory.create(AliasQualifierEnum.ROOT,
                aliasValue,
                0L,
                rootId,
                aliasValue);
        final var request = new SyncAliasRequest(alias);
        return aliasShard.getService().execute(request)
                .map(SyncAliasResponse::getCreated)
                .invoke(created -> {
                    if (created) {
                        log.info("Created alias \"{}\" for the root \"{}\"",
                                aliasValue, rootId);
                    }
                })
                .replaceWith(alias);
    }
}
