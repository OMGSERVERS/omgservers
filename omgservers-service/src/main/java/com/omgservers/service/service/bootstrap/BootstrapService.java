package com.omgservers.service.service.bootstrap;

import io.smallrye.mutiny.Uni;

public interface BootstrapService {

    Uni<Void> bootstrapServerIndex();

    Uni<Void> bootstrapServiceRoot();

    Uni<Void> bootstrapAdminUser();

    Uni<Void> bootstrapSupportUser();

    Uni<Void> bootstrapRegistryUser();

    Uni<Void> bootstrapBuilderUser();

    Uni<Void> bootstrapServiceUser();

    Uni<Void> bootstrapDefaultPool();
}
