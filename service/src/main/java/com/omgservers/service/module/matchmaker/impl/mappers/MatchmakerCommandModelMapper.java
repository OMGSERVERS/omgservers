package com.omgservers.service.module.matchmaker.impl.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandQualifierEnum;
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
public class MatchmakerCommandModelMapper {

    final ObjectMapper objectMapper;

    public MatchmakerCommandModel fromRow(final Row row) {
        final var matchmakerCommand = new MatchmakerCommandModel();
        matchmakerCommand.setId(row.getLong("id"));
        matchmakerCommand.setIdempotencyKey(row.getString("idempotency_key"));
        matchmakerCommand.setMatchmakerId(row.getLong("matchmaker_id"));
        matchmakerCommand.setCreated(row.getOffsetDateTime("created").toInstant());
        matchmakerCommand.setModified(row.getOffsetDateTime("modified").toInstant());
        final var qualifier = MatchmakerCommandQualifierEnum.valueOf(row.getString("qualifier"));
        matchmakerCommand.setQualifier(qualifier);
        matchmakerCommand.setDeleted(row.getBoolean("deleted"));
        try {
            final var body = objectMapper.readValue(row.getString("body"), qualifier.getBodyClass());
            matchmakerCommand.setBody(body);
        } catch (IOException e) {
            throw new ServerSideConflictException(ExceptionQualifierEnum.DB_DATA_CORRUPTED,
                    "matchmaker command can't be parsed, matchmakerCommand=" + matchmakerCommand, e);
        }
        return matchmakerCommand;
    }
}
