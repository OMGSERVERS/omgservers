package com.omgservers.service.entrypoint.registry.impl.service.webService;

import com.omgservers.schema.entrypoint.registry.HandleEventsRegistryRequest;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<Void> handleEvents(HandleEventsRegistryRequest request);
}
