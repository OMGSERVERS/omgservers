package com.omgservers.service.server.service.bootstrap.impl.method.bootstrapRelayJob;

import io.smallrye.mutiny.Uni;

public interface BootstrapRelayJobMethod {
    Uni<Void> bootstrapRelayJob();
}
