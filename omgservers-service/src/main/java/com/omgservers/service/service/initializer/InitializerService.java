package com.omgservers.service.service.initializer;

import io.smallrye.mutiny.Uni;

public interface InitializerService {

    Uni<Void> initialize();
}
