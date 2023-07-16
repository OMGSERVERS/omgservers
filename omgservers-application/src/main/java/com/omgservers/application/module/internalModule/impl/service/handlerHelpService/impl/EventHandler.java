package com.omgservers.application.module.internalModule.impl.service.handlerHelpService.impl;

import com.omgservers.application.module.internalModule.model.event.EventModel;
import com.omgservers.application.module.internalModule.model.event.EventQualifierEnum;
import io.smallrye.mutiny.Uni;

public interface EventHandler {
    EventQualifierEnum getQualifier();

    Uni<Boolean> handle(EventModel event);
}
