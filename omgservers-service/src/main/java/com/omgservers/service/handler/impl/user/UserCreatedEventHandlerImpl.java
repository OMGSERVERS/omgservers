package com.omgservers.service.handler.impl.user;

import com.omgservers.schema.model.entity.EntityQualifierEnum;
import com.omgservers.schema.model.user.UserModel;
import com.omgservers.schema.shard.user.GetUserRequest;
import com.omgservers.schema.shard.user.GetUserResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.user.UserCreatedEventBodyModel;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.master.entity.EntityMaster;
import com.omgservers.service.operation.entity.CreateEntityOperation;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import com.omgservers.service.shard.alias.AliasShard;
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
    final EntityMaster entityMaster;

    final GetServiceConfigOperation getServiceConfigOperation;
    final CreateEntityOperation createEntityOperation;

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
                    log.debug("Created, {}", user);

                    final var idempotencyKey = event.getId().toString();
                    return switch (user.getRole()) {
                        case ADMIN -> createEntityOperation.execute(EntityQualifierEnum.ADMIN_USER,
                                userId,
                                idempotencyKey);
                        case SUPPORT -> createEntityOperation.execute(EntityQualifierEnum.SUPPORT_USER,
                                userId,
                                idempotencyKey);
                        case SERVICE -> createEntityOperation.execute(EntityQualifierEnum.SERVICE_USER,
                                userId,
                                idempotencyKey);
                        case DEVELOPER -> createEntityOperation.execute(EntityQualifierEnum.DEVELOPER_USER,
                                userId,
                                idempotencyKey);
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
}
