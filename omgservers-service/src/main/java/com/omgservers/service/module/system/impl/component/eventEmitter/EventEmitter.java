package com.omgservers.service.module.system.impl.component.eventEmitter;

import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.MutinyEmitter;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;

@Slf4j
@ApplicationScoped
public class EventEmitter {

    final MutinyEmitter<String> forwardedEventEmitter;
    final MutinyEmitter<Long> serviceEventEmitter;

    public EventEmitter(@Channel("outgoing-events") MutinyEmitter<Long> serviceEventEmitter,
                        @Channel("forwarded-events") MutinyEmitter<String> forwardedEventEmitter) {
        this.serviceEventEmitter = serviceEventEmitter;
        this.forwardedEventEmitter = forwardedEventEmitter;
    }

    public Uni<Void> sendEvent(final Long eventId) {
        log.trace("Send event, eventId={}", eventId);
        return serviceEventEmitter.send(eventId);
    }

    public Uni<Void> forwardEvent(final String eventMessage) {
        log.info("Forward event, eventMessage={}", eventMessage);
        return forwardedEventEmitter.send(eventMessage);
    }
}
