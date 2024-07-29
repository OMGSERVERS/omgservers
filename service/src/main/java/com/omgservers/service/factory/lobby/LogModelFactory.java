package com.omgservers.service.factory.lobby;

import com.omgservers.schema.model.log.LogModel;
import com.omgservers.service.server.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class LogModelFactory {

    final GenerateIdOperation generateIdOperation;

    public LogModel create(final String message) {
        final var id = generateIdOperation.generateId();
        return create(id, message);
    }

    public LogModel create(final Long id,
                           final String message) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var log = new LogModel();
        log.setId(id);
        log.setCreated(now);
        log.setMessage(message);
        return log;
    }
}
