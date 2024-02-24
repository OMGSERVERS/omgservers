package com.omgservers.service.module.system.impl.service.eventService.impl.method.relayEvents;

import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.MutinyEmitter;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Channel;

@ApplicationScoped
public class OutboxEventEmitter {

    final MutinyEmitter<Long> outboxEventsEmitter;

    public OutboxEventEmitter(@Channel("outbox-events") MutinyEmitter<Long> outboxEventsEmitter) {
        this.outboxEventsEmitter = outboxEventsEmitter;
    }

    public Uni<Void> send(final Long eventId) {
        return outboxEventsEmitter.send(eventId);
    }
}
