package com.omgservers.service.shard.match.impl.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.match.MatchConfigDto;
import com.omgservers.schema.model.match.MatchModel;
import com.omgservers.service.exception.ServerSideConflictException;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchModelMapper {

    final ObjectMapper objectMapper;

    public MatchModel execute(final Row row) {
        final var match = new MatchModel();
        match.setId(row.getLong("id"));
        match.setIdempotencyKey(row.getString("idempotency_key"));
        match.setCreated(row.getOffsetDateTime("created").toInstant());
        match.setModified(row.getOffsetDateTime("modified").toInstant());
        match.setMatchmakerId(row.getLong("matchmaker_id"));
        match.setRuntimeId(row.getLong("runtime_id"));
        match.setDeleted(row.getBoolean("deleted"));
        try {
            match.setConfig(objectMapper.readValue(row.getString("config"), MatchConfigDto.class));
        } catch (IOException e) {
            throw new ServerSideConflictException(ExceptionQualifierEnum.DB_DATA_CORRUPTED,
                    "match config can't be parsed, match=" + match, e);
        }
        return match;
    }
}
