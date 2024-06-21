package com.omgservers.service.module.system.impl.service.bootstrapService.impl.method.bootstrapSchedulerJob;

import io.smallrye.mutiny.Uni;

public interface BootstrapSchedulerJobMethod {
    Uni<Void> bootstrapSchedulerJob();
}
