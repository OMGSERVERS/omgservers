package com.omgservers.service.server.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.index.IndexConfigModel;
import com.omgservers.schema.model.index.IndexModel;
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
public class IndexModelMapper {

    final ObjectMapper objectMapper;

    public IndexModel fromRow(final Row row) {
        final var index = new IndexModel();
        index.setId(row.getLong("id"));
        index.setIdempotencyKey(row.getString("idempotency_key"));
        index.setCreated(row.getOffsetDateTime("created").toInstant());
        index.setModified(row.getOffsetDateTime("modified").toInstant());
        index.setDeleted(row.getBoolean("deleted"));
        try {
            index.setConfig(objectMapper.readValue(row.getString("config"), IndexConfigModel.class));
        } catch (IOException e) {
            throw new ServerSideConflictException(ExceptionQualifierEnum.DB_DATA_CORRUPTED,
                    "index config can't be parsed, index=" + index, e);
        }
        return index;
    }
}
