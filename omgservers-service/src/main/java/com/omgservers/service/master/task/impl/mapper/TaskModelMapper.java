package com.omgservers.service.master.task.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.task.TaskModel;
import com.omgservers.schema.model.task.TaskQualifierEnum;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TaskModelMapper {

    final ObjectMapper objectMapper;

    public TaskModel execute(final Row row) {
        final var task = new TaskModel();
        task.setId(row.getLong("id"));
        task.setIdempotencyKey(row.getString("idempotency_key"));
        task.setCreated(row.getOffsetDateTime("created").toInstant());
        task.setModified(row.getOffsetDateTime("modified").toInstant());
        task.setQualifier(TaskQualifierEnum.valueOf(row.getString("qualifier")));
        task.setShardKey(row.getLong("shard_key"));
        task.setEntityId(row.getLong("entity_id"));
        task.setDeleted(row.getBoolean("deleted"));
        return task;
    }
}
