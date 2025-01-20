package com.omgservers.service.shard.matchmaker.impl.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.matchmakerAssignment.MatchmakerAssignmentModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchmakerAssignmentModelMapper {

    final ObjectMapper objectMapper;

    public MatchmakerAssignmentModel execute(final Row row) {
        final var matchmakerAssignment = new MatchmakerAssignmentModel();
        matchmakerAssignment.setId(row.getLong("id"));
        matchmakerAssignment.setIdempotencyKey(row.getString("idempotency_key"));
        matchmakerAssignment.setMatchmakerId(row.getLong("matchmaker_id"));
        matchmakerAssignment.setCreated(row.getOffsetDateTime("created").toInstant());
        matchmakerAssignment.setModified(row.getOffsetDateTime("modified").toInstant());
        matchmakerAssignment.setClientId(row.getLong("client_id"));
        matchmakerAssignment.setDeleted(row.getBoolean("deleted"));
        return matchmakerAssignment;
    }
}
