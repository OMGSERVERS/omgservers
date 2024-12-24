package com.omgservers.service.operation.getIdByTenant;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.module.alias.FindAliasRequest;
import com.omgservers.schema.module.alias.FindAliasResponse;
import com.omgservers.service.configuration.DefaultAliasConfiguration;
import com.omgservers.service.module.alias.AliasModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetIdByTenantOperationImpl implements GetIdByTenantOperation {

    final AliasModule aliasModule;

    @Override
    public Uni<Long> execute(final String tenant) {
        try {
            final var tenantId = Long.valueOf(tenant);
            return Uni.createFrom().item(tenantId);
        } catch (NumberFormatException e) {
            return findTenantAlias(tenant)
                    .map(AliasModel::getEntityId);
        }
    }

    Uni<AliasModel> findTenantAlias(final String tenantAlias) {
        final var request = new FindAliasRequest(DefaultAliasConfiguration.GLOBAL_SHARD_KEY, tenantAlias);
        return aliasModule.getService().execute(request)
                .map(FindAliasResponse::getAlias);
    }
}
