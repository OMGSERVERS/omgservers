package com.omgservers.module.matchmaker.impl.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.matchClient.MatchClientModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchClientMapper {

    final ObjectMapper objectMapper;

    public MatchClientModel fromRow(Row row) {
        MatchClientModel matchClient = new MatchClientModel();
        matchClient.setId(row.getLong("id"));
        matchClient.setMatchmakerId(row.getLong("matchmaker_id"));
        matchClient.setMatchId(row.getLong("match_id"));
        matchClient.setCreated(row.getOffsetDateTime("created").toInstant());
        matchClient.setModified(row.getOffsetDateTime("modified").toInstant());
        matchClient.setUserId(row.getLong("user_id"));
        matchClient.setClientId(row.getLong("client_id"));
        return matchClient;
    }
}
