package com.omgservers.service.module.matchmaker.impl.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.request.MatchmakerRequestModel;
import com.omgservers.model.request.MatchmakerRequestConfigModel;
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
public class MatchmakerRequestModelMapper {

    final ObjectMapper objectMapper;

    public MatchmakerRequestModel fromRow(final Row row) {
        final var matchmakerRequest = new MatchmakerRequestModel();
        matchmakerRequest.setId(row.getLong("id"));
        matchmakerRequest.setIdempotencyKey(row.getString("idempotency_key"));
        matchmakerRequest.setMatchmakerId(row.getLong("matchmaker_id"));
        matchmakerRequest.setCreated(row.getOffsetDateTime("created").toInstant());
        matchmakerRequest.setModified(row.getOffsetDateTime("modified").toInstant());
        matchmakerRequest.setUserId(row.getLong("user_id"));
        matchmakerRequest.setClientId(row.getLong("client_id"));
        matchmakerRequest.setMode(row.getString("mode"));
        matchmakerRequest.setDeleted(row.getBoolean("deleted"));
        try {
            matchmakerRequest.setConfig(objectMapper.readValue(row.getString("config"), MatchmakerRequestConfigModel.class));
        } catch (IOException e) {
            throw new ServerSideConflictException(ExceptionQualifierEnum.DB_DATA_CORRUPTED,
                    "matchmakerRequest config can't be parsed, matchmakerRequest=" + matchmakerRequest, e);
        }
        return matchmakerRequest;
    }
}
