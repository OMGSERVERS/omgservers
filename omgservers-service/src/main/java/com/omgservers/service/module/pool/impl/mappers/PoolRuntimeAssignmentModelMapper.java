package com.omgservers.service.module.pool.impl.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.poolRuntimeAssignment.PoolRuntimeAssignmentConfigModel;
import com.omgservers.model.poolRuntimeAssignment.PoolRuntimeAssignmentModel;
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
public class PoolRuntimeAssignmentModelMapper {

    final ObjectMapper objectMapper;

    public PoolRuntimeAssignmentModel fromRow(final Row row) {
        final var poolRuntimeAssignment = new PoolRuntimeAssignmentModel();
        poolRuntimeAssignment.setId(row.getLong("id"));
        poolRuntimeAssignment.setIdempotencyKey(row.getString("idempotency_key"));
        poolRuntimeAssignment.setPoolId(row.getLong("pool_id"));
        poolRuntimeAssignment.setCreated(row.getOffsetDateTime("created").toInstant());
        poolRuntimeAssignment.setModified(row.getOffsetDateTime("modified").toInstant());
        poolRuntimeAssignment.setRuntimeId(row.getLong("runtime_id"));
        poolRuntimeAssignment.setServerId(row.getLong("server_id"));
        poolRuntimeAssignment.setDeleted(row.getBoolean("deleted"));
        try {
            poolRuntimeAssignment.setConfig(objectMapper.readValue(row.getString("config"),
                    PoolRuntimeAssignmentConfigModel.class));
        } catch (IOException e) {
            throw new ServerSideConflictException(ExceptionQualifierEnum.DB_DATA_CORRUPTED,
                    "pool runtime assignment config can't be parsed, " +
                            "poolRuntimeAssignment=" + poolRuntimeAssignment, e);
        }
        return poolRuntimeAssignment;
    }
}
