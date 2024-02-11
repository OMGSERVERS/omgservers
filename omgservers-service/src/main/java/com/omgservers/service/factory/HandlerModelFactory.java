package com.omgservers.service.factory;

import com.omgservers.model.handler.HandlerModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class HandlerModelFactory {

    final GenerateIdOperation generateIdOperation;

    public HandlerModel create() {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var handler = new HandlerModel();
        handler.setId(generateIdOperation.generateId());
        handler.setCreated(now);
        handler.setModified(now);
        handler.setDeleted(false);
        return handler;
    }
}
