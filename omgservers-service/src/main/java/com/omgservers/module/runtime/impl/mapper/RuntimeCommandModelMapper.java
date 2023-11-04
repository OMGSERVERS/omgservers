package com.omgservers.module.runtime.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RuntimeCommandModelMapper {

    final ObjectMapper objectMapper;

    public RuntimeCommandModel fromRow(Row row) {
        final var runtimeCommand = new RuntimeCommandModel();
        runtimeCommand.setId(row.getLong("id"));
        runtimeCommand.setRuntimeId(row.getLong("runtime_id"));
        runtimeCommand.setCreated(row.getOffsetDateTime("created").toInstant());
        runtimeCommand.setModified(row.getOffsetDateTime("modified").toInstant());
        final var qualifier = RuntimeCommandQualifierEnum.valueOf(row.getString("qualifier"));
        runtimeCommand.setQualifier(qualifier);
        runtimeCommand.setDeleted(row.getBoolean("deleted"));
        try {
            final var body = objectMapper.readValue(row.getString("body"), qualifier.getBodyClass());
            runtimeCommand.setBody(body);
        } catch (IOException e) {
            throw new ServerSideConflictException("runtime command can't be parsed, " +
                    "runtimeCommand=" + runtimeCommand, e);
        }

        return runtimeCommand;
    }
}
