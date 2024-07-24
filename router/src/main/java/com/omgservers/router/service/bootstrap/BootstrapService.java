package com.omgservers.router.service.bootstrap;

import io.smallrye.mutiny.Uni;

public interface BootstrapService {

    Uni<Void> bootstrap();
}
