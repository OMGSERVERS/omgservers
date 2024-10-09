package com.omgservers.service.factory.runtime;

import com.omgservers.schema.model.runtimeCommand.RuntimeCommandBodyDto;
import com.omgservers.schema.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RuntimeCommandModelFactory {

    final GenerateIdOperation generateIdOperation;

    public RuntimeCommandModel create(final Long runtimeId,
                                      final RuntimeCommandBodyDto body) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, runtimeId, body, idempotencyKey);
    }

    public RuntimeCommandModel create(final Long runtimeId,
                                      final RuntimeCommandBodyDto body,
                                      final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, runtimeId, body, idempotencyKey);
    }

    public RuntimeCommandModel create(final Long id,
                                      final Long runtimeId,
                                      final RuntimeCommandBodyDto body,
                                      final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var runtimeCommand = new RuntimeCommandModel();
        runtimeCommand.setId(id);
        runtimeCommand.setIdempotencyKey(idempotencyKey);
        runtimeCommand.setRuntimeId(runtimeId);
        runtimeCommand.setCreated(now);
        runtimeCommand.setModified(now);
        runtimeCommand.setQualifier(body.getQualifier());
        runtimeCommand.setBody(body);
        runtimeCommand.setDeleted(false);
        return runtimeCommand;
    }
}
