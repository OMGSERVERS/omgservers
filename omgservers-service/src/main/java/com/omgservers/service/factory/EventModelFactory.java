package com.omgservers.service.factory;

import com.omgservers.model.event.EventBodyModel;
import com.omgservers.model.event.EventModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class EventModelFactory {

    final GenerateIdOperation generateIdOperation;

    public EventModel create(EventBodyModel body) {
        final var id = generateIdOperation.generateId();
        return create(id, body);
    }

    public EventModel create(Long id, EventBodyModel body) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var event = new EventModel();
        event.setId(id);
        event.setCreated(now);
        event.setModified(now);
        event.setQualifier(body.getQualifier());
        event.setBody(body);
        event.setDelayed(now);
        event.setAttempts(0);
        event.setDeleted(false);
        return event;
    }
}
