package com.omgservers.dispatcher.server.initializer;

import io.smallrye.mutiny.Uni;

public interface InitializerService {

    Uni<Void> initialize();
}
