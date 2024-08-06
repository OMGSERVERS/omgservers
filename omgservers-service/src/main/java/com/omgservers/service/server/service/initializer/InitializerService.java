package com.omgservers.service.server.service.initializer;

import io.smallrye.mutiny.Uni;

public interface InitializerService {

    Uni<Void> initialize();
}
