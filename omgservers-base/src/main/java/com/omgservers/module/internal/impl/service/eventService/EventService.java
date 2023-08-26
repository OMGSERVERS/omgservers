package com.omgservers.module.internal.impl.service.eventService;

import io.smallrye.mutiny.Uni;

public interface EventService {

    Uni<Void> startEventDispatcher();
}
