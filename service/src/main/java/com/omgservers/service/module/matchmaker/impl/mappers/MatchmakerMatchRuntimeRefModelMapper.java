package com.omgservers.service.module.matchmaker.impl.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.matchmakerMatchRuntimeRef.MatchmakerMatchRuntimeRefModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchmakerMatchRuntimeRefModelMapper {

    final ObjectMapper objectMapper;

    public MatchmakerMatchRuntimeRefModel fromRow(final Row row) {
        final var matchmakerMatchRuntimeRef = new MatchmakerMatchRuntimeRefModel();
        matchmakerMatchRuntimeRef.setId(row.getLong("id"));
        matchmakerMatchRuntimeRef.setIdempotencyKey(row.getString("idempotency_key"));
        matchmakerMatchRuntimeRef.setMatchmakerId(row.getLong("matchmaker_id"));
        matchmakerMatchRuntimeRef.setMatchId(row.getLong("match_id"));
        matchmakerMatchRuntimeRef.setCreated(row.getOffsetDateTime("created").toInstant());
        matchmakerMatchRuntimeRef.setModified(row.getOffsetDateTime("modified").toInstant());
        matchmakerMatchRuntimeRef.setRuntimeId(row.getLong("runtime_id"));
        matchmakerMatchRuntimeRef.setDeleted(row.getBoolean("deleted"));
        return matchmakerMatchRuntimeRef;
    }
}
