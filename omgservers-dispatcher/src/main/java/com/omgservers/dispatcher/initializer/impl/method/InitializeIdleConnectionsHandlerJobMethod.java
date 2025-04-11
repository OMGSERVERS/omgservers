package com.omgservers.dispatcher.initializer.impl.method;

import io.smallrye.mutiny.Uni;

public interface InitializeIdleConnectionsHandlerJobMethod {
    Uni<Void> execute();
}
