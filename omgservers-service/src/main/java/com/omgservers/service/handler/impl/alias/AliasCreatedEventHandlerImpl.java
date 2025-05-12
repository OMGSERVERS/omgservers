package com.omgservers.service.handler.impl.alias;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.model.alias.AliasQualifierEnum;
import com.omgservers.schema.shard.alias.GetAliasRequest;
import com.omgservers.schema.shard.alias.GetAliasResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.alias.AliasCreatedEventBodyModel;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.operation.alias.CreatePtrAliasOperation;
import com.omgservers.service.shard.alias.AliasShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class AliasCreatedEventHandlerImpl implements EventHandler {

    final AliasShard aliasShard;

    final CreatePtrAliasOperation createPtrAliasOperation;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.ALIAS_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("{}", event);

        final var body = (AliasCreatedEventBodyModel) event.getBody();
        final var shardKey = body.getShardKey();
        final var aliasId = body.getId();

        final var idempotencyKey = event.getId().toString();

        return getAlias(shardKey, aliasId)
                .flatMap(alias -> {
                    log.debug("Created, {}", alias);

                    if (alias.getQualifier().equals(AliasQualifierEnum.PTR)) {
                        return Uni.createFrom().voidItem();
                    } else {
                        final var entityId = alias.getEntityId();
                        final var aliasValue = alias.getValue();
                        return createPtrAliasOperation.execute(entityId, aliasValue, idempotencyKey);
                    }
                })
                .replaceWithVoid();
    }

    Uni<AliasModel> getAlias(final String shardKey, final Long id) {
        final var request = new GetAliasRequest(shardKey, id);
        return aliasShard.getService().execute(request)
                .map(GetAliasResponse::getAlias);
    }
}

