package com.omgservers.service.service.bootstrap;

import io.smallrye.mutiny.Uni;

public interface BootstrapService {

    Uni<Void> bootstrap();
}
