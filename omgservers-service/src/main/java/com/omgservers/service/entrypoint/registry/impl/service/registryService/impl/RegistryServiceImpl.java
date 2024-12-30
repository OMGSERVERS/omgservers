package com.omgservers.service.entrypoint.registry.impl.service.registryService.impl;

import com.omgservers.schema.entrypoint.registry.HandleEventsRegistryRequest;
import com.omgservers.service.entrypoint.registry.impl.service.registryService.RegistryService;
import com.omgservers.service.entrypoint.registry.impl.service.registryService.impl.method.HandleEventsMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class RegistryServiceImpl implements RegistryService {

    final HandleEventsMethod handleEventsMethod;

    @Override
    public Uni<Void> handleEvents(@Valid final HandleEventsRegistryRequest request) {
        return handleEventsMethod.handleEvents(request);
    }
}
