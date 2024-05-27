package com.omgservers.service.module.system.impl.service.bootstrapService;

import io.smallrye.mutiny.Uni;

public interface BootstrapService {

    Uni<Void> bootstrapSchema();

    Uni<Void> bootstrapIndex();

    Uni<Void> bootstrapAdmin();

    Uni<Void> bootstrapRoot();

    Uni<Void> bootstrapRelay();
}
