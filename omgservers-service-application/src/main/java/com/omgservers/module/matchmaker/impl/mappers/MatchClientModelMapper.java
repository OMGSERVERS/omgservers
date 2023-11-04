package com.omgservers.module.matchmaker.impl.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.model.matchClient.MatchClientConfigModel;
import com.omgservers.model.matchClient.MatchClientModel;
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

    public MatchClientModel fromRow(Row row) {
        MatchClientModel matchClient = new MatchClientModel();
        matchClient.setId(row.getLong("id"));
        matchClient.setMatchmakerId(row.getLong("matchmaker_id"));
        matchClient.setMatchId(row.getLong("match_id"));
        matchClient.setCreated(row.getOffsetDateTime("created").toInstant());
        matchClient.setModified(row.getOffsetDateTime("modified").toInstant());
        matchClient.setUserId(row.getLong("user_id"));
        matchClient.setClientId(row.getLong("client_id"));
        matchClient.setGroupName(row.getString("group_name"));
        try {
            matchClient.setConfig(objectMapper.readValue(row.getString("config"), MatchClientConfigModel.class));
        } catch (IOException e) {
            throw new ServerSideConflictException("match client config can't be parsed, matchClient=" + matchClient, e);
        }
        return matchClient;
    }
}
