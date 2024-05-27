package com.omgservers.service.module.system.impl.service.bootstrapService.impl.method.bootstrapRelay;

import io.smallrye.mutiny.Uni;

public interface BootstrapRelayMethod {
    Uni<Void> bootstrapRelay();
}
