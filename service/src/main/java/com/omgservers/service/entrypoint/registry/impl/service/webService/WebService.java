package com.omgservers.service.entrypoint.registry.impl.service.webService;

import com.omgservers.registry.HandleEventsRegistryRequest;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<Void> handleEvent(HandleEventsRegistryRequest request);
}
