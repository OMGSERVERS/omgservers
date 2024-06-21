package com.omgservers.service.service;

import io.smallrye.mutiny.Uni;

public interface BootstrapService {

    Uni<Void> bootstrap();
}
