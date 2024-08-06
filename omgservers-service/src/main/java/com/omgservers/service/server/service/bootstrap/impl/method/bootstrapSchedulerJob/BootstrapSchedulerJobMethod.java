package com.omgservers.service.server.service.bootstrap.impl.method.bootstrapSchedulerJob;

import io.smallrye.mutiny.Uni;

public interface BootstrapSchedulerJobMethod {
    Uni<Void> bootstrapSchedulerJob();
}
