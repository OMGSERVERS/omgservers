package com.omgservers.service.module.queue.impl.mappers;

import com.omgservers.schema.model.queue.QueueModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class QueueModelMapper {

    public QueueModel execute(final Row row) {
        final var queue = new QueueModel();
        queue.setId(row.getLong("id"));
        queue.setIdempotencyKey(row.getString("idempotency_key"));
        queue.setCreated(row.getOffsetDateTime("created").toInstant());
        queue.setModified(row.getOffsetDateTime("modified").toInstant());
        queue.setTenantId(row.getLong("tenant_id"));
        queue.setDeploymentId(row.getLong("deployment_id"));
        queue.setDeleted(row.getBoolean("deleted"));
        return queue;
    }
}
