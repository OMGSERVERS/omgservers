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
class CreatePtrAliasOperationImpl implements CreatePtrAliasOperation {

    final AliasShard aliasShard;

    final AliasModelFactory aliasModelFactory;

    @Override
    public Uni<Boolean> execute(final Long entityId,
                                final String aliasValue,
                                final String idempotencyKey) {
        final var alias = aliasModelFactory.create(AliasQualifierEnum.PTR,
                entityId.toString(),
                entityId,
                entityId,
                aliasValue,
                idempotencyKey);
        final var syncAliasRequest = new SyncAliasRequest(alias);
        return aliasShard.getService().execute(syncAliasRequest)
                .invoke(response -> {
                    if (response.getCreated()) {
                        log.info("Created PTR alias \"{}\" for the entity \"{}\"", aliasValue, entityId);
                    }
                })
                .map(SyncAliasResponse::getCreated);
    }
}
