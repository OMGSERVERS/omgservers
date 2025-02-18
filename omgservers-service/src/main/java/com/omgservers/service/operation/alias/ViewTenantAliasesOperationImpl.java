package com.omgservers.service.operation.alias;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.module.alias.ViewAliasesRequest;
import com.omgservers.schema.module.alias.ViewAliasesResponse;
import com.omgservers.service.configuration.GlobalShardConfiguration;
import com.omgservers.service.shard.alias.AliasShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewTenantAliasesOperationImpl implements ViewTenantAliasesOperation {

    final AliasShard aliasShard;

    @Override
    public Uni<List<AliasModel>> execute(final Long tenantId) {
        final var request = new ViewAliasesRequest();
        request.setShardKey(GlobalShardConfiguration.GLOBAL_SHARD_KEY);
        request.setEntityId(tenantId);

        return aliasShard.getService().execute(request)
                .map(ViewAliasesResponse::getAliases);
    }
}
