package com.omgservers.service.service.initializer.impl.method;

import io.smallrye.mutiny.Uni;

public interface InitializeRelayJobMethod {
    Uni<Void> execute();
}