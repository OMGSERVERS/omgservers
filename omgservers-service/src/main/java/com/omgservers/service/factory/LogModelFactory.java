package com.omgservers.service.factory;

import com.omgservers.service.operation.generateId.GenerateIdOperation;
import com.omgservers.model.log.LogModel;
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
        Instant now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        LogModel log = new LogModel();
        log.setId(id);
        log.setCreated(now);
        log.setMessage(message);
        return log;
    }
}
