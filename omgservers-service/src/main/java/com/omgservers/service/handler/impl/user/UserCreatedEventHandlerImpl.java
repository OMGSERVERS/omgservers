package com.omgservers.service.handler.impl.user;

import com.omgservers.schema.model.rootEntityRef.RootEntityRefQualifierEnum;
import com.omgservers.schema.model.user.UserModel;
import com.omgservers.schema.module.root.rootEntityRef.SyncRootEntityRefRequest;
import com.omgservers.schema.module.root.rootEntityRef.SyncRootEntityRefResponse;
import com.omgservers.schema.module.user.GetUserRequest;
import com.omgservers.schema.module.user.GetUserResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.user.UserCreatedEventBodyModel;
import com.omgservers.service.factory.root.RootEntityRefModelFactory;
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
public class UserCreatedEventHandlerImpl implements EventHandler {

    final UserModule userModule;
    final RootModule rootModule;

    final GetConfigOperation getConfigOperation;

    final RootEntityRefModelFactory rootEntityRefModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.USER_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (UserCreatedEventBodyModel) event.getBody();
        final var userId = body.getId();

        return getUser(userId)
                .flatMap(user -> {
                    log.info("User was created, id={}, role={}", userId, user.getRole());

                    final var idempotencyKey = event.getId().toString();
                    return switch (user.getRole()) {
                        case ADMIN -> syncRootUserRef(userId, idempotencyKey,
                                RootEntityRefQualifierEnum.ADMIN_USER);
                        case SUPPORT -> syncRootUserRef(userId, idempotencyKey,
                                RootEntityRefQualifierEnum.SUPPORT_USER);
                        case REGISTRY -> syncRootUserRef(userId, idempotencyKey,
                                RootEntityRefQualifierEnum.REGISTRY_USER);
                        case BUILDER -> syncRootUserRef(userId, idempotencyKey,
                                RootEntityRefQualifierEnum.BUILDER_USER);
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
        return userModule.getUserService().getUser(request)
                .map(GetUserResponse::getUser);
    }

    Uni<Boolean> syncRootUserRef(final Long tenantId,
                                 final String idempotencyKey,
                                 final RootEntityRefQualifierEnum refQualifier) {
        final var rootId = getConfigOperation.getServiceConfig().defaults().rootId();
        final var rootEntityRef = rootEntityRefModelFactory.create(idempotencyKey, rootId,
                refQualifier,
                tenantId);
        final var request = new SyncRootEntityRefRequest(rootEntityRef);
        return rootModule.getRootService().syncRootEntityRefWithIdempotency(request)
                .map(SyncRootEntityRefResponse::getCreated);
    }
}
