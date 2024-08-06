package com.omgservers.service.entrypoint.registry.impl.service.registryService.impl.method.handleEvents;

import com.omgservers.schema.entrypoint.registry.handleEvents.HandleEventsRegistryRequest;
import io.smallrye.mutiny.Uni;

public interface HandleEventsMethod {
    Uni<Void> handleEvents(HandleEventsRegistryRequest request);
}
