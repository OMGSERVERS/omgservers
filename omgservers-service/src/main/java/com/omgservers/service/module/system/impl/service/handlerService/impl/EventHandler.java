package com.omgservers.service.module.system.impl.service.handlerService.impl;

import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import io.smallrye.mutiny.Uni;

public interface EventHandler {
    EventQualifierEnum getQualifier();

    Uni<Boolean> handle(EventModel event);
}
