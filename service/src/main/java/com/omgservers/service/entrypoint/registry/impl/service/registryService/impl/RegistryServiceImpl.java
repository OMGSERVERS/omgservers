package com.omgservers.service.entrypoint.registry.impl.service.registryService.impl;

import com.omgservers.registry.HandleEventsRegistryRequest;
import com.omgservers.service.entrypoint.registry.impl.service.registryService.RegistryService;
import com.omgservers.service.entrypoint.registry.impl.service.registryService.impl.method.HandleEventMethod;
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

    final HandleEventMethod handleEventMethod;

    @Override
    public Uni<Void> handleEvent(@Valid final HandleEventsRegistryRequest request) {
        return handleEventMethod.handleEvent(request);
    }
}
