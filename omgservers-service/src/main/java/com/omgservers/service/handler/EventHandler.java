package com.omgservers.service.handler;

import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import io.smallrye.mutiny.Uni;

public interface EventHandler {
    EventQualifierEnum getQualifier();

    Uni<Void> handle(EventModel event);
}
