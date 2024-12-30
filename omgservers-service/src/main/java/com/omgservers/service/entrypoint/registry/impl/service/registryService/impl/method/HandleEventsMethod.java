package com.omgservers.service.entrypoint.registry.impl.service.registryService.impl.method;

import com.omgservers.schema.entrypoint.registry.HandleEventsRegistryRequest;
import io.smallrye.mutiny.Uni;

public interface HandleEventsMethod {
    Uni<Void> handleEvents(HandleEventsRegistryRequest request);
}
