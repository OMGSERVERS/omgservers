package com.omgservers.service.factory.queue;

import com.omgservers.schema.model.queueRequest.QueueRequestModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class QueueRequestModelFactory {

    final GenerateIdOperation generateIdOperation;

    public QueueRequestModel create(final Long queueId,
                                    final Long clientId) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, queueId, clientId, idempotencyKey);
    }

    public QueueRequestModel create(final Long queueId,
                                    final Long clientId,
                                    final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, queueId, clientId, idempotencyKey);
    }

    public QueueRequestModel create(final Long id,
                                    final Long queueId,
                                    final Long clientId,
                                    final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var queueRequest = new QueueRequestModel();
        queueRequest.setId(id);
        queueRequest.setIdempotencyKey(idempotencyKey);
        queueRequest.setQueueId(queueId);
        queueRequest.setCreated(now);
        queueRequest.setModified(now);
        queueRequest.setClientId(clientId);
        queueRequest.setDeleted(Boolean.FALSE);

        return queueRequest;
    }
}
