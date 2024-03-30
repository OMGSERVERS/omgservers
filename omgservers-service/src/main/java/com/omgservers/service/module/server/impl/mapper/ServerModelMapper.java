package com.omgservers.service.module.server.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.server.ServerConfigModel;
import com.omgservers.model.server.ServerModel;
import com.omgservers.model.server.ServerQualifierEnum;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideConflictException;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ServerModelMapper {

    final ObjectMapper objectMapper;

    public ServerModel fromRow(final Row row) {
        final var server = new ServerModel();
        server.setId(row.getLong("id"));
        server.setIdempotencyKey(row.getString("idempotency_key"));
        server.setCreated(row.getOffsetDateTime("created").toInstant());
        server.setModified(row.getOffsetDateTime("modified").toInstant());
        server.setPoolId(row.getLong("pool_id"));
        server.setQualifier(ServerQualifierEnum.valueOf(row.getString("qualifier")));
        server.setUri(URI.create(row.getString("uri")));
        server.setCpuCount(row.getInteger("cpu_count"));
        server.setCpuUsed(row.getInteger("cpu_used"));
        server.setMemorySize(row.getInteger("memory_size"));
        server.setMemoryUsed(row.getInteger("memory_used"));
        server.setDeleted(row.getBoolean("deleted"));
        try {
            final var config = objectMapper.readValue(row.getString("config"), ServerConfigModel.class);
            server.setConfig(config);
        } catch (IOException e) {
            throw new ServerSideConflictException(ExceptionQualifierEnum.DB_DATA_CORRUPTED,
                    "server config can't be parsed, server=" + server, e);
        }
        return server;
    }
}
