package com.omgservers.service.handler;

import com.omgservers.model.entitiy.EntityQualifierEnum;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.UserCreatedEventBodyModel;
import com.omgservers.service.factory.EntityModelFactory;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.module.system.impl.service.handlerService.impl.EventHandler;
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

    final EntityModelFactory entityModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.USER_CREATED;
    }

    @Override
    public Uni<Boolean> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (UserCreatedEventBodyModel) event.getBody();
        final var userId = body.getId();

        return userModule.getShortcutService().getUser(userId)
                .flatMap(user -> {
                    log.info("User was created, user={}", userId);

                    final var entity = entityModelFactory.create(userId, EntityQualifierEnum.USER);
                    return systemModule.getShortcutService().syncEntity(entity);
                })
                .replaceWith(true);
    }
}
