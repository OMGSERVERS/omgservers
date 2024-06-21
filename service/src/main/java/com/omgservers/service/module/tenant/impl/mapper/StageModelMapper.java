package com.omgservers.service.module.tenant.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.stage.StageModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class StageModelMapper {

    final ObjectMapper objectMapper;

    public StageModel fromRow(final Row row) {
        final var stage = new StageModel();
        stage.setId(row.getLong("id"));
        stage.setTenantId(row.getLong("tenant_id"));
        stage.setProjectId(row.getLong("project_id"));
        stage.setCreated(row.getOffsetDateTime("created").toInstant());
        stage.setModified(row.getOffsetDateTime("modified").toInstant());
        stage.setIdempotencyKey(row.getString("idempotency_key"));
        stage.setSecret(row.getString("secret"));
        stage.setDeleted(row.getBoolean("deleted"));
        return stage;
    }
}
