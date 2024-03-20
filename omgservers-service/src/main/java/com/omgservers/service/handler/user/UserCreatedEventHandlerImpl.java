package com.omgservers.service.handler.user;

import com.omgservers.model.dto.user.GetUserRequest;
import com.omgservers.model.dto.user.GetUserResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.module.UserCreatedEventBodyModel;
import com.omgservers.model.user.UserModel;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.module.user.UserModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class UserCreatedEventHandlerImpl implements EventHandler {

    final SystemModule systemModule;
    final UserModule userModule;

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

                    return Uni.createFrom().voidItem();
                })
                .replaceWithVoid();
    }

    Uni<UserModel> getUser(final Long id) {
        final var request = new GetUserRequest(id);
        return userModule.getUserService().getUser(request)
                .map(GetUserResponse::getUser);
    }
}
