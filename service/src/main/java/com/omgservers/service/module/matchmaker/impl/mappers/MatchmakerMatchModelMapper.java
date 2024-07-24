package com.omgservers.service.module.matchmaker.impl.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.matchmakerMatch.MatchmakerMatchConfigModel;
import com.omgservers.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.model.matchmakerMatch.MatchmakerMatchStatusEnum;
import com.omgservers.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideConflictException;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchmakerMatchModelMapper {

    final ObjectMapper objectMapper;

    public MatchmakerMatchModel fromRow(final Row row) {
        final var matchmakerMatch = new MatchmakerMatchModel();
        matchmakerMatch.setId(row.getLong("id"));
        matchmakerMatch.setIdempotencyKey(row.getString("idempotency_key"));
        matchmakerMatch.setMatchmakerId(row.getLong("matchmaker_id"));
        matchmakerMatch.setCreated(row.getOffsetDateTime("created").toInstant());
        matchmakerMatch.setModified(row.getOffsetDateTime("modified").toInstant());
        matchmakerMatch.setRuntimeId(row.getLong("runtime_id"));
        matchmakerMatch.setStatus(MatchmakerMatchStatusEnum.valueOf(row.getString("status")));
        matchmakerMatch.setDeleted(row.getBoolean("deleted"));
        try {
            matchmakerMatch.setConfig(objectMapper.readValue(row.getString("config"),
                    MatchmakerMatchConfigModel.class));
        } catch (IOException e) {
            throw new ServerSideConflictException(ExceptionQualifierEnum.DB_DATA_CORRUPTED,
                    "matchmakerMatch config can't be parsed, matchmakerMatch=" + matchmakerMatch, e);
        }
        return matchmakerMatch;
    }
}
