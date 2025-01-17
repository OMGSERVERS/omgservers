package com.omgservers.service.module.queue.impl.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.queueRequest.QueueRequestModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class QueueRequestModelMapper {

    final ObjectMapper objectMapper;

    public QueueRequestModel execute(final Row row) {
        final var queueRequest = new QueueRequestModel();
        queueRequest.setId(row.getLong("id"));
        queueRequest.setIdempotencyKey(row.getString("idempotency_key"));
        queueRequest.setQueueId(row.getLong("queue_id"));
        queueRequest.setCreated(row.getOffsetDateTime("created").toInstant());
        queueRequest.setModified(row.getOffsetDateTime("modified").toInstant());
        queueRequest.setClientId(row.getLong("client_id"));
        queueRequest.setDeleted(row.getBoolean("deleted"));
        return queueRequest;
    }
}
