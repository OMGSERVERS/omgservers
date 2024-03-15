package com.omgservers.service.module.matchmaker.impl.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.match.MatchConfigModel;
import com.omgservers.model.match.MatchModel;
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
public class MatchModelMapper {

    final ObjectMapper objectMapper;

    public MatchModel fromRow(final Row row) {
        final var match = new MatchModel();
        match.setId(row.getLong("id"));
        match.setMatchmakerId(row.getLong("matchmaker_id"));
        match.setCreated(row.getOffsetDateTime("created").toInstant());
        match.setModified(row.getOffsetDateTime("modified").toInstant());
        match.setRuntimeId(row.getLong("runtime_id"));
        match.setStopped(row.getBoolean("stopped"));
        try {
            match.setConfig(objectMapper.readValue(row.getString("config"), MatchConfigModel.class));
        } catch (IOException e) {
            throw new ServerSideConflictException(ExceptionQualifierEnum.DB_DATA_CORRUPTED,
                    "match config can't be parsed, match=" + match, e);
        }
        match.setDeleted(row.getBoolean("deleted"));
        return match;
    }
}
