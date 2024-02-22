package com.omgservers.service.module.matchmaker.impl.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.matchRuntimeRef.MatchRuntimeRefModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchRuntimeRefModelMapper {

    final ObjectMapper objectMapper;

    public MatchRuntimeRefModel fromRow(Row row) {
        final var matchRuntimeRef = new MatchRuntimeRefModel();
        matchRuntimeRef.setId(row.getLong("id"));
        matchRuntimeRef.setMatchmakerId(row.getLong("matchmaker_id"));
        matchRuntimeRef.setMatchId(row.getLong("match_id"));
        matchRuntimeRef.setCreated(row.getOffsetDateTime("created").toInstant());
        matchRuntimeRef.setModified(row.getOffsetDateTime("modified").toInstant());
        matchRuntimeRef.setRuntimeId(row.getLong("runtime_id"));
        matchRuntimeRef.setDeleted(row.getBoolean("deleted"));
        return matchRuntimeRef;
    }
}
