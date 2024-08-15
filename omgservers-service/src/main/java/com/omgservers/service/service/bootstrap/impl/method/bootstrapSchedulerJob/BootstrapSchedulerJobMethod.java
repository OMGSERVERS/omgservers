package com.omgservers.service.service.bootstrap.impl.method.bootstrapSchedulerJob;

import io.smallrye.mutiny.Uni;

public interface BootstrapSchedulerJobMethod {
    Uni<Void> bootstrapSchedulerJob();
}
