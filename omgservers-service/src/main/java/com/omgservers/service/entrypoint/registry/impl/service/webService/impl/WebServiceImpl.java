package com.omgservers.service.entrypoint.registry.impl.service.webService.impl;

import com.omgservers.schema.entrypoint.registry.HandleEventsRegistryRequest;
import com.omgservers.service.entrypoint.registry.impl.service.registryService.RegistryService;
import com.omgservers.service.entrypoint.registry.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class WebServiceImpl implements WebService {

    final RegistryService registryService;

    @Override
    public Uni<Void> handleEvents(final HandleEventsRegistryRequest request) {
        return registryService.handleEvents(request);
    }
}
