package com.omgservers.service.handler.impl.user;

import com.omgservers.schema.module.root.rootEntityRef.DeleteRootEntityRefRequest;
import com.omgservers.schema.module.root.rootEntityRef.DeleteRootEntityRefResponse;
import com.omgservers.schema.module.root.rootEntityRef.FindRootEntityRefRequest;
import com.omgservers.schema.module.root.rootEntityRef.FindRootEntityRefResponse;
import com.omgservers.schema.module.user.GetUserRequest;
import com.omgservers.schema.module.user.GetUserResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.user.UserDeletedEventBodyModel;
import com.omgservers.schema.model.rootEntityRef.RootEntityRefModel;
import com.omgservers.schema.model.user.UserModel;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.root.RootModule;
import com.omgservers.service.module.user.UserModule;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class UserDeletedEventHandlerImpl implements EventHandler {

    final UserModule userModule;
    final RootModule rootModule;

    final GetConfigOperation getConfigOperation;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.USER_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (UserDeletedEventBodyModel) event.getBody();
        final var userId = body.getId();

        return getUser(userId)
                .flatMap(user -> {
                    log.info("User was deleted, user={}", userId);

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
        return userModule.getUserService().getUser(request)
                .map(GetUserResponse::getUser);
    }

    Uni<Void> findAndDeleteRootUserRef(final Long tenantId) {
        final var rootId = getConfigOperation.getServiceConfig().defaults().rootId();
        return findRootEntityRef(rootId, tenantId)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithNull()
                .onItem().ifNotNull().transformToUni(rootEntityRef ->
                        deleteRootEntityRef(rootId, rootEntityRef.getId()))
                .replaceWithVoid();
    }

    Uni<RootEntityRefModel> findRootEntityRef(final Long rootId,
                                              final Long tenantId) {
        final var request = new FindRootEntityRefRequest(rootId, tenantId);
        return rootModule.getRootService().findRootEntityRef(request)
                .map(FindRootEntityRefResponse::getRootEntityRef);
    }

    Uni<Boolean> deleteRootEntityRef(final Long rootId, final Long id) {
        final var request = new DeleteRootEntityRefRequest(rootId, id);
        return rootModule.getRootService().deleteRootEntityRef(request)
                .map(DeleteRootEntityRefResponse::getDeleted);
    }
}
