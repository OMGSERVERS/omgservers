package com.omgservers.service.module.matchmaker.impl.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentConfigDto;
import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentModel;
import com.omgservers.service.exception.ServerSideConflictException;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchmakerMatchAssignmentModelMapper {

    final ObjectMapper objectMapper;

    public MatchmakerMatchAssignmentModel execute(final Row row) {
        final var matchmakerMatchAssignment = new MatchmakerMatchAssignmentModel();
        matchmakerMatchAssignment.setId(row.getLong("id"));
        matchmakerMatchAssignment.setIdempotencyKey(row.getString("idempotency_key"));
        matchmakerMatchAssignment.setMatchmakerId(row.getLong("matchmaker_id"));
        matchmakerMatchAssignment.setMatchId(row.getLong("match_id"));
        matchmakerMatchAssignment.setCreated(row.getOffsetDateTime("created").toInstant());
        matchmakerMatchAssignment.setModified(row.getOffsetDateTime("modified").toInstant());
        matchmakerMatchAssignment.setUserId(row.getLong("user_id"));
        matchmakerMatchAssignment.setClientId(row.getLong("client_id"));
        matchmakerMatchAssignment.setGroupName(row.getString("group_name"));
        matchmakerMatchAssignment.setDeleted(row.getBoolean("deleted"));
        try {
            matchmakerMatchAssignment.setConfig(objectMapper
                    .readValue(row.getString("config"), MatchmakerMatchAssignmentConfigDto.class));
        } catch (IOException e) {
            throw new ServerSideConflictException(ExceptionQualifierEnum.DB_DATA_CORRUPTED,
                    "Matchmaker match assignment config can't be parsed, matchmakerMatchAssignment=" +
                            matchmakerMatchAssignment, e);
        }
        return matchmakerMatchAssignment;
    }
}
