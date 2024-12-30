package com.omgservers.service.entrypoint.registry.impl.service.registryService;

import com.omgservers.schema.entrypoint.registry.HandleEventsRegistryRequest;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface RegistryService {

    Uni<Void> handleEvents(@Valid HandleEventsRegistryRequest request);

}
