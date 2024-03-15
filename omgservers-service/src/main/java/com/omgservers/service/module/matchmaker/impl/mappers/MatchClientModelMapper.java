package com.omgservers.service.module.matchmaker.impl.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.matchClient.MatchClientConfigModel;
import com.omgservers.model.matchClient.MatchClientModel;
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

    public MatchClientModel fromRow(final Row row) {
        final var matchClient = new MatchClientModel();
        matchClient.setId(row.getLong("id"));
        matchClient.setMatchmakerId(row.getLong("matchmaker_id"));
        matchClient.setMatchId(row.getLong("match_id"));
        matchClient.setCreated(row.getOffsetDateTime("created").toInstant());
        matchClient.setModified(row.getOffsetDateTime("modified").toInstant());
        matchClient.setUserId(row.getLong("user_id"));
        matchClient.setClientId(row.getLong("client_id"));
        matchClient.setGroupName(row.getString("group_name"));
        matchClient.setDeleted(row.getBoolean("deleted"));
        try {
            matchClient.setConfig(objectMapper.readValue(row.getString("config"), MatchClientConfigModel.class));
        } catch (IOException e) {
            throw new ServerSideConflictException(ExceptionQualifierEnum.DB_DATA_CORRUPTED,
                    "match client config can't be parsed, matchClient=" + matchClient, e);
        }
        return matchClient;
    }
}
