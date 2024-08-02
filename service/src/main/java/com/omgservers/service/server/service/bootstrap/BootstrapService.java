package com.omgservers.service.server.service.bootstrap;

import io.smallrye.mutiny.Uni;

public interface BootstrapService {

    Uni<Void> bootstrapDatabaseSchema();

    Uni<Void> bootstrapServerIndex();

    Uni<Void> bootstrapServiceRoot();

    Uni<Void> bootstrapAdminUser();

    Uni<Void> bootstrapSupportUser();

    Uni<Void> bootstrapRouterUser();

    Uni<Void> bootstrapRegistryUser();

    Uni<Void> bootstrapBuilderUser();

    Uni<Void> bootstrapServiceUser();

    Uni<Void> bootstrapDefaultPool();

    Uni<Void> bootstrapDockerHost();

    Uni<Void> bootstrapRelayJob();

    Uni<Void> bootstrapSchedulerJob();
}
