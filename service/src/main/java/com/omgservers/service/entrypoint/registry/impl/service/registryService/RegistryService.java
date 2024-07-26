package com.omgservers.service.entrypoint.registry.impl.service.registryService;

import com.omgservers.registry.HandleEventsRegistryRequest;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface RegistryService {

    Uni<Void> handleEvent(@Valid HandleEventsRegistryRequest request);
}
