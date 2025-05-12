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
class CreateTenantAliasOperationImpl implements CreateTenantAliasOperation {

    final AliasShard aliasShard;

    final AliasModelFactory aliasModelFactory;

    @Override
    public Uni<CreateTenantAliasResult> execute(final Long tenantId, final String aliasValue) {
        final var alias = aliasModelFactory.create(AliasQualifierEnum.TENANT,
                aliasValue,
                0L,
                tenantId,
                aliasValue);
        final var syncAliasRequest = new SyncAliasRequest(alias);
        return aliasShard.getService().execute(syncAliasRequest)
                .map(SyncAliasResponse::getCreated)
                .invoke(created -> {
                    if (created) {
                        log.info("Created alias \"{}\" for the tenant \"{}\"",
                                aliasValue, tenantId);
                    }
                })
                .map(created -> new CreateTenantAliasResult(alias, created));
    }
}
