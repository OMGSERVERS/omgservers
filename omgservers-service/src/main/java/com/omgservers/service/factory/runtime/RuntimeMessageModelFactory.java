package com.omgservers.service.factory.runtime;

import com.omgservers.schema.message.MessageBodyDto;
import com.omgservers.schema.model.runtimeMessage.RuntimeMessageModel;
import com.omgservers.service.operation.server.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RuntimeMessageModelFactory {

    final GenerateIdOperation generateIdOperation;

    public RuntimeMessageModel create(final Long runtimeId,
                                      final MessageBodyDto body) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, runtimeId, body, idempotencyKey);
    }

    public RuntimeMessageModel create(final Long runtimeId,
                                      final MessageBodyDto body,
                                      final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, runtimeId, body, idempotencyKey);
    }

    public RuntimeMessageModel create(final Long id,
                                      final Long runtimeId,
                                      final MessageBodyDto body,
                                      final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var runtimeMessage = new RuntimeMessageModel();
        runtimeMessage.setId(id);
        runtimeMessage.setIdempotencyKey(idempotencyKey);
        runtimeMessage.setRuntimeId(runtimeId);
        runtimeMessage.setCreated(now);
        runtimeMessage.setModified(now);
        runtimeMessage.setQualifier(body.getQualifier());
        runtimeMessage.setBody(body);
        runtimeMessage.setDeleted(false);
        return runtimeMessage;
    }
}
