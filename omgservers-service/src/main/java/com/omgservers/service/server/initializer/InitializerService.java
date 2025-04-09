package com.omgservers.service.server.initializer;

import io.smallrye.mutiny.Uni;

public interface InitializerService {

    Uni<Void> initialize();
}
