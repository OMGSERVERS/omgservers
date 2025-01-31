package com.omgservers.service.operation.alias;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.model.alias.AliasQualifierEnum;
import com.omgservers.schema.module.alias.SyncAliasRequest;
import com.omgservers.service.configuration.DefaultAliasConfiguration;
import com.omgservers.service.configuration.GlobalShardConfiguration;
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
    public Uni<AliasModel> execute(Long tenantId, String aliasValue) {
        final var alias = aliasModelFactory.create(AliasQualifierEnum.TENANT,
                GlobalShardConfiguration.GLOBAL_SHARD_KEY,
                DefaultAliasConfiguration.GLOBAL_TENANTS_GROUP,
                tenantId,
                aliasValue);
        final var syncAliasRequest = new SyncAliasRequest(alias);
        return aliasShard.getService().execute(syncAliasRequest)
                .invoke(response -> {
                    if (response.getCreated()) {
                        log.info("The alias \"{}\" for the tenant \"{}\" was created",
                                aliasValue, tenantId);
                    }
                })
                .replaceWith(alias);
    }
}
