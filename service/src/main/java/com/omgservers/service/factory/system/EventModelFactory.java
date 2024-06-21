package com.omgservers.service.factory.system;

import com.omgservers.model.event.EventBodyModel;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventStatusEnum;
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

    public EventModel create(final EventBodyModel body) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, body, idempotencyKey);
    }

    public EventModel create(final EventBodyModel body,
                             final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, body, idempotencyKey);
    }

    public EventModel create(final Long id,
                             final EventBodyModel body,
                             final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var event = new EventModel();
        event.setId(id);
        event.setIdempotencyKey(idempotencyKey);
        event.setCreated(now);
        event.setModified(now);
        event.setDelayed(now);
        event.setQualifier(body.getQualifier());
        event.setBody(body);
        event.setStatus(EventStatusEnum.CREATED);
        event.setDeleted(false);
        return event;
    }
}
