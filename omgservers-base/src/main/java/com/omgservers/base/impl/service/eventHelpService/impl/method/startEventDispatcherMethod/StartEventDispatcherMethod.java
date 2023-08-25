package com.omgservers.base.impl.service.eventHelpService.impl.method.startEventDispatcherMethod;

import io.smallrye.mutiny.Uni;

public interface StartEventDispatcherMethod {
    Uni<Void> startEventDispatcher();
}
