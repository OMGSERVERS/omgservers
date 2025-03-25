package com.omgservers.service.shard.runtime.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.runtime.RuntimeConfigDto;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.model.runtime.RuntimeQualifierEnum;
import com.omgservers.service.exception.ServerSideConflictException;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RuntimeModelMapper {

    final ObjectMapper objectMapper;

    public RuntimeModel execute(final Row row) {
        final var runtime = new RuntimeModel();
        runtime.setId(row.getLong("id"));
        runtime.setIdempotencyKey(row.getString("idempotency_key"));
        runtime.setCreated(row.getOffsetDateTime("created").toInstant());
        runtime.setModified(row.getOffsetDateTime("modified").toInstant());
        runtime.setDeploymentId(row.getLong("deployment_id"));
        runtime.setQualifier(RuntimeQualifierEnum.valueOf(row.getString("qualifier")));
        runtime.setDeleted(row.getBoolean("deleted"));
        runtime.setUserId(row.getLong("user_id"));
        try {
            runtime.setConfig(objectMapper.readValue(row.getString("config"), RuntimeConfigDto.class));
        } catch (IOException e) {
            throw new ServerSideConflictException(ExceptionQualifierEnum.DB_DATA_CORRUPTED,
                    "runtime can't be parsed, runtime=" + runtime, e);
        }
        return runtime;
    }
}
