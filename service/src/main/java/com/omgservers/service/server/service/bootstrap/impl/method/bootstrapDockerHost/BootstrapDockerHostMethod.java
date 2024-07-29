package com.omgservers.service.server.service.bootstrap.impl.method.bootstrapDockerHost;

import io.smallrye.mutiny.Uni;

public interface BootstrapDockerHostMethod {
    Uni<Void> bootstrapDockerHost();
}
