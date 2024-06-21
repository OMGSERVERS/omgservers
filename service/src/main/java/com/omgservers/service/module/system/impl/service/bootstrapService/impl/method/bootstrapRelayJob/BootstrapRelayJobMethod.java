package com.omgservers.service.module.system.impl.service.bootstrapService.impl.method.bootstrapRelayJob;

import io.smallrye.mutiny.Uni;

public interface BootstrapRelayJobMethod {
    Uni<Void> bootstrapRelayJob();
}
