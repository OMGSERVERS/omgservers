package com.omgservers.dispatcher.initializer;

import io.smallrye.mutiny.Uni;

public interface InitializerService {

    Uni<Void> initialize();
}
