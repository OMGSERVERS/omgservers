package com.omgservers.service.server.service.bootstrap.impl.method.bootstrapDefaultPool;

import io.smallrye.mutiny.Uni;

public interface BootstrapDefaultPoolMethod {
    Uni<Void> bootstrapDefaultPool();
}
