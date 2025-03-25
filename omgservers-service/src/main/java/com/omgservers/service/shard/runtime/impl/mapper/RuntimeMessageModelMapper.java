package com.omgservers.service.shard.runtime.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.message.MessageQualifierEnum;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.runtimeMessage.RuntimeMessageModel;
import com.omgservers.service.exception.ServerSideConflictException;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RuntimeMessageModelMapper {

    final ObjectMapper objectMapper;

    public RuntimeMessageModel execute(final Row row) {
        final var runtimeMessage = new RuntimeMessageModel();
        runtimeMessage.setId(row.getLong("id"));
        runtimeMessage.setIdempotencyKey(row.getString("idempotency_key"));
        runtimeMessage.setRuntimeId(row.getLong("runtime_id"));
        runtimeMessage.setCreated(row.getOffsetDateTime("created").toInstant());
        runtimeMessage.setModified(row.getOffsetDateTime("modified").toInstant());
        final var qualifier = MessageQualifierEnum.valueOf(row.getString("qualifier"));
        runtimeMessage.setQualifier(qualifier);
        runtimeMessage.setDeleted(row.getBoolean("deleted"));
        try {
            final var body = objectMapper.readValue(row.getString("body"), qualifier.getBodyClass());
            runtimeMessage.setBody(body);
        } catch (IOException e) {
            throw new ServerSideConflictException(ExceptionQualifierEnum.DB_DATA_CORRUPTED,
                    "runtime message can't be parsed, runtimeMessage=" + runtimeMessage, e);
        }

        return runtimeMessage;
    }
}
