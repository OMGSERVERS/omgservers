package com.omgservers.service.module.system.impl.service.eventService.impl.method.relayEvents;

import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.MutinyEmitter;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;

@Slf4j
@ApplicationScoped
public class OutboxEventEmitter {

    final MutinyEmitter<Long> outboxEventsEmitter;

    public OutboxEventEmitter(@Channel("outbox-events") MutinyEmitter<Long> outboxEventsEmitter) {
        this.outboxEventsEmitter = outboxEventsEmitter;
    }

    public Uni<Void> send(final Long eventId) {
        log.trace("Send, eventId={}", eventId);
        return outboxEventsEmitter.send(eventId);
    }
}
