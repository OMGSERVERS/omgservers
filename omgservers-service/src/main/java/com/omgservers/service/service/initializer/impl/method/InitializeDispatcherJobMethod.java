package com.omgservers.service.service.initializer.impl.method;

import io.smallrye.mutiny.Uni;

public interface InitializeDispatcherJobMethod {
    Uni<Void> execute();
}
