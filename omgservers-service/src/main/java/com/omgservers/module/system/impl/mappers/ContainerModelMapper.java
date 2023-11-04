package com.omgservers.module.system.impl.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.model.container.ContainerConfigModel;
import com.omgservers.model.container.ContainerModel;
import com.omgservers.model.container.ContainerQualifierEnum;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

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
        container.setEntityId(row.getLong("entity_id"));
        container.setQualifier(ContainerQualifierEnum.valueOf(row.getString("qualifier")));
        container.setImage(row.getString("image"));
        container.setDeleted(row.getBoolean("deleted"));
        try {
            container.setConfig(objectMapper.readValue(row.getString("config"), ContainerConfigModel.class));
        } catch (IOException e) {
            throw new ServerSideConflictException("container config can't be parsed, container=" + container, e);
        }
        return container;
    }
}
