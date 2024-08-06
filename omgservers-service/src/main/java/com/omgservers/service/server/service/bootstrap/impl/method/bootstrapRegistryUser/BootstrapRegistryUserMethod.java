package com.omgservers.service.server.service.bootstrap.impl.method.bootstrapRegistryUser;

import io.smallrye.mutiny.Uni;

public interface BootstrapRegistryUserMethod {
    Uni<Void> bootstrapRegistryUser();
}
