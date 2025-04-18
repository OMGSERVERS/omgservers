package com.omgservers.service.handler.impl.user;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.model.rootEntityRef.RootEntityRefQualifierEnum;
import com.omgservers.schema.model.user.UserModel;
import com.omgservers.schema.shard.alias.FindAliasRequest;
import com.omgservers.schema.shard.alias.FindAliasResponse;
import com.omgservers.schema.shard.root.rootEntityRef.SyncRootEntityRefRequest;
import com.omgservers.schema.shard.root.rootEntityRef.SyncRootEntityRefResponse;
import com.omgservers.schema.shard.user.GetUserRequest;
import com.omgservers.schema.shard.user.GetUserResponse;
import com.omgservers.service.configuration.DefaultAliasConfiguration;
import com.omgservers.service.configuration.GlobalShardConfiguration;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.user.UserCreatedEventBodyModel;
import com.omgservers.service.factory.root.RootEntityRefModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import com.omgservers.service.shard.alias.AliasShard;
import com.omgservers.service.shard.root.RootShard;
import com.omgservers.service.shard.user.UserShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class UserCreatedEventHandlerImpl implements EventHandler {

    final AliasShard aliasShard;
    final UserShard userShard;
    final RootShard rootShard;

    final GetServiceConfigOperation getServiceConfigOperation;

    final RootEntityRefModelFactory rootEntityRefModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.USER_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (UserCreatedEventBodyModel) event.getBody();
        final var userId = body.getId();

        return getUser(userId)
                .flatMap(user -> {
                    log.debug("Created, {}", user);

                    final var idempotencyKey = event.getId().toString();
                    return switch (user.getRole()) {
                        case ADMIN -> syncRootUserRef(userId, idempotencyKey,
                                RootEntityRefQualifierEnum.ADMIN_USER);
                        case SUPPORT -> syncRootUserRef(userId, idempotencyKey,
                                RootEntityRefQualifierEnum.SUPPORT_USER);
                        case SERVICE -> syncRootUserRef(userId, idempotencyKey,
                                RootEntityRefQualifierEnum.SERVICE_USER);
                        case DEVELOPER -> syncRootUserRef(userId, idempotencyKey,
                                RootEntityRefQualifierEnum.DEVELOPER_USER);
                        default -> Uni.createFrom().voidItem();
                    };
                })
                .replaceWithVoid();
    }

    Uni<UserModel> getUser(final Long id) {
        final var request = new GetUserRequest(id);
        return userShard.getService().execute(request)
                .map(GetUserResponse::getUser);
    }

    Uni<Boolean> syncRootUserRef(final Long tenantId,
                                 final String idempotencyKey,
                                 final RootEntityRefQualifierEnum refQualifier) {
        return findRootEntityAlias()
                .flatMap(alias -> {
                    final var rootId = alias.getEntityId();
                    final var rootEntityRef = rootEntityRefModelFactory.create(idempotencyKey, rootId,
                            refQualifier,
                            tenantId);
                    final var request = new SyncRootEntityRefRequest(rootEntityRef);
                    return rootShard.getService().executeWithIdempotency(request)
                            .map(SyncRootEntityRefResponse::getCreated);
                });
    }

    Uni<AliasModel> findRootEntityAlias() {
        final var request = new FindAliasRequest(GlobalShardConfiguration.GLOBAL_SHARD_KEY,
                DefaultAliasConfiguration.GLOBAL_ENTITIES_GROUP,
                DefaultAliasConfiguration.ROOT_ENTITY_ALIAS);
        return aliasShard.getService().execute(request)
                .map(FindAliasResponse::getAlias);
    }
}
