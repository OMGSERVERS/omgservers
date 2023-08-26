package com.omgservers.base.module.internal.impl.service.eventService.impl.method.startEventDispatcher;

import io.smallrye.mutiny.Uni;

public interface StartEventDispatcherMethod {
    Uni<Void> startEventDispatcher();
}
