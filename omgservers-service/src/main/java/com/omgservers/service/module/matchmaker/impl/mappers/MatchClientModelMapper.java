package com.omgservers.service.module.matchmaker.impl.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.matchmakerMatchClient.MatchmakerMatchClientConfigModel;
import com.omgservers.model.matchmakerMatchClient.MatchmakerMatchClientModel;
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
public class MatchClientModelMapper {

    final ObjectMapper objectMapper;

    public MatchmakerMatchClientModel fromRow(final Row row) {
        final var matchmakerMatchClient = new MatchmakerMatchClientModel();
        matchmakerMatchClient.setId(row.getLong("id"));
        matchmakerMatchClient.setIdempotencyKey(row.getString("idempotency_key"));
        matchmakerMatchClient.setMatchmakerId(row.getLong("matchmaker_id"));
        matchmakerMatchClient.setMatchId(row.getLong("match_id"));
        matchmakerMatchClient.setCreated(row.getOffsetDateTime("created").toInstant());
        matchmakerMatchClient.setModified(row.getOffsetDateTime("modified").toInstant());
        matchmakerMatchClient.setUserId(row.getLong("user_id"));
        matchmakerMatchClient.setClientId(row.getLong("client_id"));
        matchmakerMatchClient.setGroupName(row.getString("group_name"));
        matchmakerMatchClient.setDeleted(row.getBoolean("deleted"));
        try {
            matchmakerMatchClient.setConfig(objectMapper
                    .readValue(row.getString("config"), MatchmakerMatchClientConfigModel.class));
        } catch (IOException e) {
            throw new ServerSideConflictException(ExceptionQualifierEnum.DB_DATA_CORRUPTED,
                    "match client config can't be parsed, matchmakerMatchClient=" + matchmakerMatchClient, e);
        }
        return matchmakerMatchClient;
    }
}
