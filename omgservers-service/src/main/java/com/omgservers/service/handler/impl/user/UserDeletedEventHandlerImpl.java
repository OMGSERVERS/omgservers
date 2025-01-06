package com.omgservers.service.handler.impl.user;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.model.rootEntityRef.RootEntityRefModel;
import com.omgservers.schema.model.user.UserModel;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.schema.module.alias.FindAliasRequest;
import com.omgservers.schema.module.alias.FindAliasResponse;
import com.omgservers.schema.module.root.rootEntityRef.DeleteRootEntityRefRequest;
import com.omgservers.schema.module.root.rootEntityRef.DeleteRootEntityRefResponse;
import com.omgservers.schema.module.root.rootEntityRef.FindRootEntityRefRequest;
import com.omgservers.schema.module.root.rootEntityRef.FindRootEntityRefResponse;
import com.omgservers.schema.module.user.GetUserRequest;
import com.omgservers.schema.module.user.GetUserResponse;
import com.omgservers.service.configuration.DefaultAliasConfiguration;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.user.UserDeletedEventBodyModel;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.alias.AliasModule;
import com.omgservers.service.module.root.RootModule;
import com.omgservers.service.module.user.UserModule;
import com.omgservers.service.operation.getServiceConfig.GetServiceConfigOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class UserDeletedEventHandlerImpl implements EventHandler {

    final AliasModule aliasModule;
    final UserModule userModule;
    final RootModule rootModule;

    final GetServiceConfigOperation getServiceConfigOperation;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.USER_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (UserDeletedEventBodyModel) event.getBody();
        final var userId = body.getId();

        return getUser(userId)
                .flatMap(user -> {
                    log.debug("Deleted, {}", user);

                    if (user.getRole().equals(UserRoleEnum.DEVELOPER)) {
                        return findAndDeleteRootUserRef(userId);
                    } else {
                        return Uni.createFrom().voidItem();
                    }
                })
                .replaceWithVoid();
    }

    Uni<UserModel> getUser(final Long id) {
        final var request = new GetUserRequest(id);
        return userModule.getService().getUser(request)
                .map(GetUserResponse::getUser);
    }

    Uni<Void> findAndDeleteRootUserRef(final Long tenantId) {
        return findRootEntityAlias()
                .flatMap(alias -> {
                    final var rootId = alias.getEntityId();
                    return findRootEntityRef(rootId, tenantId)
                            .onFailure(ServerSideNotFoundException.class)
                            .recoverWithNull()
                            .onItem().ifNotNull().transformToUni(rootEntityRef ->
                                    deleteRootEntityRef(rootId, rootEntityRef.getId()))
                            .replaceWithVoid();
                });
    }

    Uni<AliasModel> findRootEntityAlias() {
        final var request = new FindAliasRequest(DefaultAliasConfiguration.GLOBAL_SHARD_KEY,
                DefaultAliasConfiguration.GLOBAL_ENTITIES_GROUP,
                DefaultAliasConfiguration.ROOT_ENTITY_ALIAS);
        return aliasModule.getService().execute(request)
                .map(FindAliasResponse::getAlias);
    }

    Uni<RootEntityRefModel> findRootEntityRef(final Long rootId,
                                              final Long tenantId) {
        final var request = new FindRootEntityRefRequest(rootId, tenantId);
        return rootModule.getService().findRootEntityRef(request)
                .map(FindRootEntityRefResponse::getRootEntityRef);
    }

    Uni<Boolean> deleteRootEntityRef(final Long rootId, final Long id) {
        final var request = new DeleteRootEntityRefRequest(rootId, id);
        return rootModule.getService().deleteRootEntityRef(request)
                .map(DeleteRootEntityRefResponse::getDeleted);
    }
}
