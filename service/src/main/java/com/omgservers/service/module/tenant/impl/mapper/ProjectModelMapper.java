package com.omgservers.service.module.tenant.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.project.ProjectModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ProjectModelMapper {

    final ObjectMapper objectMapper;

    public ProjectModel fromRow(final Row row) {
        final var project = new ProjectModel();
        project.setId(row.getLong("id"));
        project.setTenantId(row.getLong("tenant_id"));
        project.setCreated(row.getOffsetDateTime("created").toInstant());
        project.setModified(row.getOffsetDateTime("modified").toInstant());
        project.setIdempotencyKey(row.getString("idempotency_key"));
        project.setDeleted(row.getBoolean("deleted"));

        return project;
    }
}
