package com.omgservers.service.module.runtime.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.runtimeClient.RuntimeClientConfigModel;
import com.omgservers.model.runtimeClient.RuntimeClientModel;
import com.omgservers.service.exception.ServerSideConflictException;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RuntimeClientModelMapper {

    final ObjectMapper objectMapper;

    public RuntimeClientModel fromRow(Row row) {
        final var runtimeClient = new RuntimeClientModel();
        runtimeClient.setId(row.getLong("id"));
        runtimeClient.setRuntimeId(row.getLong("runtime_id"));
        runtimeClient.setCreated(row.getOffsetDateTime("created").toInstant());
        runtimeClient.setModified(row.getOffsetDateTime("modified").toInstant());
        runtimeClient.setClientId(row.getLong("client_id"));
        runtimeClient.setLastActivity(row.getOffsetDateTime("last_activity").toInstant());
        try {
            runtimeClient.setConfig(objectMapper.readValue(row.getString("config"),
                    RuntimeClientConfigModel.class));
        } catch (IOException e) {
            throw new ServerSideConflictException("runtime client can't be parsed, runtimeClient=" + runtimeClient, e);
        }
        runtimeClient.setDeleted(row.getBoolean("deleted"));
        return runtimeClient;
    }
}
