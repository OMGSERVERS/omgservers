package com.omgservers.service.operation.alias;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.model.alias.AliasQualifierEnum;
import com.omgservers.schema.shard.alias.ViewAliasesRequest;
import com.omgservers.schema.shard.alias.ViewAliasesResponse;
import com.omgservers.service.shard.alias.AliasShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewEntityAliasesOperationImpl implements ViewEntityAliasesOperation {

    final AliasShard aliasShard;

    @Override
    public Uni<List<AliasModel>> execute(final AliasQualifierEnum qualifier,
                                         final String shardKey,
                                         final Long entityId) {
        final var request = new ViewAliasesRequest();
        request.setShardKey(shardKey);
        request.setEntityId(entityId);

        return aliasShard.getService().execute(request)
                .map(ViewAliasesResponse::getAliases)
                .map(aliases -> aliases.stream()
                        .filter(alias -> alias.getQualifier().equals(qualifier))
                        .toList());
    }
}
