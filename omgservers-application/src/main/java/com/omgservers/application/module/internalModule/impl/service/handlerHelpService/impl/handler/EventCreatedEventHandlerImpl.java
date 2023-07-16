package com.omgservers.application.module.internalModule.impl.service.handlerHelpService.impl.handler;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.impl.service.eventInternalService.request.FireEventInternalRequest;
import com.omgservers.application.module.internalModule.impl.service.handlerHelpService.impl.EventHandler;
import com.omgservers.application.module.internalModule.model.event.EventModel;
import com.omgservers.application.module.internalModule.model.event.EventQualifierEnum;
import com.omgservers.application.module.internalModule.model.event.body.EventCreatedEventBodyModel;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class EventCreatedEventHandlerImpl implements EventHandler {

    final InternalModule internalModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.EVENT_CREATED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (EventCreatedEventBodyModel) event.getBody();
        final var origin = body.getEvent();
        final var request = new FireEventInternalRequest(origin);
        return internalModule.getEventInternalService().fireEvent(request)
                .replaceWith(true);
    }
}
