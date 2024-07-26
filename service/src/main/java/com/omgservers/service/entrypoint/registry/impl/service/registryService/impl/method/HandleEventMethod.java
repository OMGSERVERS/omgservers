package com.omgservers.service.entrypoint.registry.impl.service.registryService.impl.method;

import com.omgservers.registry.HandleEventsRegistryRequest;
import io.smallrye.mutiny.Uni;

public interface HandleEventMethod {
    Uni<Void> handleEvent(HandleEventsRegistryRequest request);
}
