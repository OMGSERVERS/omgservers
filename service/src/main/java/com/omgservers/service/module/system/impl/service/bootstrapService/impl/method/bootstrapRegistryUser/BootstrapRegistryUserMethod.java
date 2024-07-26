package com.omgservers.service.module.system.impl.service.bootstrapService.impl.method.bootstrapRegistryUser;

import io.smallrye.mutiny.Uni;

public interface BootstrapRegistryUserMethod {
    Uni<Void> bootstrapRegistryUser();
}
