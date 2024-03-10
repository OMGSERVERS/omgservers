package com.omgservers.service.module.matchmaker.impl.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.matchCommand.MatchCommandModel;
import com.omgservers.model.matchCommand.MatchCommandQualifierEnum;
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
public class MatchCommandModelMapper {

    final ObjectMapper objectMapper;

    public MatchCommandModel fromRow(Row row) {
        final var matchCommand = new MatchCommandModel();
        matchCommand.setId(row.getLong("id"));
        matchCommand.setMatchmakerId(row.getLong("matchmaker_id"));
        matchCommand.setMatchId(row.getLong("match_id"));
        matchCommand.setCreated(row.getOffsetDateTime("created").toInstant());
        matchCommand.setModified(row.getOffsetDateTime("modified").toInstant());
        final var qualifier = MatchCommandQualifierEnum.valueOf(row.getString("qualifier"));
        matchCommand.setQualifier(qualifier);
        matchCommand.setDeleted(row.getBoolean("deleted"));
        try {
            final var body = objectMapper.readValue(row.getString("body"), qualifier.getBodyClass());
            matchCommand.setBody(body);
        } catch (IOException e) {
            throw new ServerSideConflictException(ExceptionQualifierEnum.DB_DATA_CORRUPTED,
                    "match command can't be parsed, matchCommand=" + matchCommand, e);
        }
        return matchCommand;
    }
}
