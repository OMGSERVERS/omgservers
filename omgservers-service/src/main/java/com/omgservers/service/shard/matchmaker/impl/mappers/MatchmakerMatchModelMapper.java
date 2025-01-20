package com.omgservers.service.shard.matchmaker.impl.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchConfigDto;
import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchStatusEnum;
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

    public MatchmakerMatchModel execute(final Row row) {
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
                    MatchmakerMatchConfigDto.class));
        } catch (IOException e) {
            throw new ServerSideConflictException(ExceptionQualifierEnum.DB_DATA_CORRUPTED,
                    "matchmakerMatch config can't be parsed, matchmakerMatch=" + matchmakerMatch, e);
        }
        return matchmakerMatch;
    }
}
