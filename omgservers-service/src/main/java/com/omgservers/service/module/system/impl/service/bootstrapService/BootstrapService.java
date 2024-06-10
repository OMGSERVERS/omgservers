package com.omgservers.service.module.system.impl.service.bootstrapService;

import io.smallrye.mutiny.Uni;

public interface BootstrapService {

    Uni<Void> bootstrapDatabaseSchema();

    Uni<Void> bootstrapServiceIndex();

    Uni<Void> bootstrapServiceRoot();

    Uni<Void> bootstrapAdminUser();

    Uni<Void> bootstrapSupportUser();

    Uni<Void> bootstrapDefaultPool();

    Uni<Void> bootstrapDockerHost();

    Uni<Void> bootstrapRelayJob();
}