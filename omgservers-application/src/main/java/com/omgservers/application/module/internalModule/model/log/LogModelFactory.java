package com.omgservers.application.module.internalModule.model.log;

import com.omgservers.application.operation.generateIdOperation.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

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
        Instant now = Instant.now();
        LogModel log = new LogModel();
        log.setId(id);
        log.setCreated(now);
        log.setMessage(message);
        return log;
    }
}
