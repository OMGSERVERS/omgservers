package com.omgservers.module.matchmaker.impl.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.model.match.MatchConfigModel;
import com.omgservers.model.match.MatchModel;
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

    public MatchModel fromRow(Row row) {
        MatchModel match = new MatchModel();
        match.setId(row.getLong("id"));
        match.setMatchmakerId(row.getLong("matchmaker_id"));
        match.setCreated(row.getOffsetDateTime("created").toInstant());
        match.setModified(row.getOffsetDateTime("modified").toInstant());
        match.setRuntimeId(row.getLong("runtime_id"));
        try {
            match.setConfig(objectMapper.readValue(row.getString("config"), MatchConfigModel.class));
        } catch (IOException e) {
            throw new ServerSideConflictException("match config can't be parsed, match=" + match, e);
        }
        return match;
    }
}
