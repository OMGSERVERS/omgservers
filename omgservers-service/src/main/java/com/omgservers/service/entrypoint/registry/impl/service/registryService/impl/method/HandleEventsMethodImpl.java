package com.omgservers.service.entrypoint.registry.impl.service.registryService.impl.method;

import com.omgservers.schema.entrypoint.registry.HandleEventsRegistryRequest;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.service.event.EventService;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HandleEventsMethodImpl implements HandleEventsMethod {

    final EventService eventService;

    final EventModelFactory eventModelFactory;
    final SecurityIdentity securityIdentity;

    @Override
    public Uni<Void> handleEvents(final HandleEventsRegistryRequest request) {
        log.info("Requested, {}", request);

        final var events = request.getEvents();
        return Multi.createFrom().iterable(events)
                .collect().asList()
                .replaceWithVoid();
    }
}
