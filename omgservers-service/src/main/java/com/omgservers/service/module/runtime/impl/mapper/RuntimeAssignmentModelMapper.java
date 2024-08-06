package com.omgservers.service.module.runtime.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentConfigModel;
import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideConflictException;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RuntimeAssignmentModelMapper {

    final ObjectMapper objectMapper;

    public RuntimeAssignmentModel fromRow(final Row row) {
        final var runtimeAssignment = new RuntimeAssignmentModel();
        runtimeAssignment.setId(row.getLong("id"));
        runtimeAssignment.setIdempotencyKey(row.getString("idempotency_key"));
        runtimeAssignment.setRuntimeId(row.getLong("runtime_id"));
        runtimeAssignment.setCreated(row.getOffsetDateTime("created").toInstant());
        runtimeAssignment.setModified(row.getOffsetDateTime("modified").toInstant());
        runtimeAssignment.setClientId(row.getLong("client_id"));
        runtimeAssignment.setLastActivity(row.getOffsetDateTime("last_activity").toInstant());
        try {
            runtimeAssignment.setConfig(objectMapper.readValue(row.getString("config"),
                    RuntimeAssignmentConfigModel.class));
        } catch (IOException e) {
            throw new ServerSideConflictException(ExceptionQualifierEnum.DB_DATA_CORRUPTED,
                    "runtime assignment can't be parsed, runtimeAssignment=" + runtimeAssignment, e);
        }
        runtimeAssignment.setDeleted(row.getBoolean("deleted"));
        return runtimeAssignment;
    }
}
