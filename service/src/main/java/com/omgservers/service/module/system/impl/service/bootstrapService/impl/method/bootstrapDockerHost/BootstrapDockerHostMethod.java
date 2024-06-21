package com.omgservers.service.module.system.impl.service.bootstrapService.impl.method.bootstrapDockerHost;

import io.smallrye.mutiny.Uni;

public interface BootstrapDockerHostMethod {
    Uni<Void> bootstrapDockerHost();
}
