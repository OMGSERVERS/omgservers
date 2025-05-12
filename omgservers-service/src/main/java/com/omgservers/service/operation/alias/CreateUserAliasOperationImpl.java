package com.omgservers.service.operation.alias;

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
class CreateUserAliasOperationImpl implements CreateUserAliasOperation {

    final AliasShard aliasShard;

    final AliasModelFactory aliasModelFactory;

    @Override
    public Uni<CreateUserAliasResult> execute(final Long userId, final String aliasValue) {
        final var alias = aliasModelFactory.create(AliasQualifierEnum.USER,
                aliasValue,
                0L,
                userId,
                aliasValue);
        final var syncAliasRequest = new SyncAliasRequest(alias);
        return aliasShard.getService().execute(syncAliasRequest)
                .map(SyncAliasResponse::getCreated)
                .invoke(created -> {
                    if (created) {
                        log.info("Created alias \"{}\" for the user \"{}\"",
                                aliasValue, userId);
                    }
                })
                .map(created -> new CreateUserAliasResult(alias, created));
    }
}
