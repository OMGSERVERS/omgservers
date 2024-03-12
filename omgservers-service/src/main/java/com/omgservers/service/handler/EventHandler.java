package com.omgservers.service.handler;

import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import io.smallrye.mutiny.Uni;

public interface EventHandler {
    EventQualifierEnum getQualifier();

    Uni<Void> handle(EventModel event);

    default String generateKey(EventModel event, String postfix) {
        return event.getId() + "_" + postfix;
    }
}
