package com.omgservers.module.system.impl.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.container.ContainerModel;
import com.omgservers.model.container.ContainerTypeEnum;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ContainerModelMapper {

    final ObjectMapper objectMapper;

    public ContainerModel fromRow(Row row) {
        final var container = new ContainerModel();
        container.setId(row.getLong("id"));
        container.setCreated(row.getOffsetDateTime("created").toInstant());
        container.setModified(row.getOffsetDateTime("modified").toInstant());
        container.setTenantId(row.getLong("tenant_id"));
        container.setVersionId(row.getLong("version_id"));
        container.setRuntimeId(row.getLong("runtime_id"));
        container.setType(ContainerTypeEnum.valueOf(row.getString("type")));
        container.setDeleted(row.getBoolean("deleted"));
        return container;
    }
}
