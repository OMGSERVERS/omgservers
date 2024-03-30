package com.omgservers.service.module.server.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.serverContainer.ServerContainerConfigModel;
import com.omgservers.model.serverContainer.ServerContainerModel;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideConflictException;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ServerContainerModelMapper {

    final ObjectMapper objectMapper;

    public ServerContainerModel fromRow(final Row row) {
        final var serverContainer = new ServerContainerModel();
        serverContainer.setId(row.getLong("id"));
        serverContainer.setIdempotencyKey(row.getString("idempotency_key"));
        serverContainer.setServerId(row.getLong("server_id"));
        serverContainer.setCreated(row.getOffsetDateTime("created").toInstant());
        serverContainer.setModified(row.getOffsetDateTime("modified").toInstant());
        serverContainer.setRuntimeId(row.getLong("runtime_id"));
        serverContainer.setImage(row.getString("image"));
        serverContainer.setCpuLimit(row.getInteger("cpu_limit"));
        serverContainer.setMemoryLimit(row.getInteger("memory_limit"));
        serverContainer.setDeleted(row.getBoolean("deleted"));
        try {
            final var config = objectMapper.readValue(row.getString("config"), ServerContainerConfigModel.class);
            serverContainer.setConfig(config);
        } catch (IOException e) {
            throw new ServerSideConflictException(ExceptionQualifierEnum.DB_DATA_CORRUPTED,
                    "server container can't be parsed, serverContainer=" + serverContainer, e);
        }
        return serverContainer;
    }
}
