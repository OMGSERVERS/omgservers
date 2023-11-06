package com.omgservers.service.module.matchmaker.impl.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.model.request.RequestConfigModel;
import com.omgservers.model.request.RequestModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RequestModelMapper {

    final ObjectMapper objectMapper;

    public RequestModel fromRow(Row row) {
        RequestModel request = new RequestModel();
        request.setId(row.getLong("id"));
        request.setMatchmakerId(row.getLong("matchmaker_id"));
        request.setCreated(row.getOffsetDateTime("created").toInstant());
        request.setModified(row.getOffsetDateTime("modified").toInstant());
        request.setUserId(row.getLong("user_id"));
        request.setClientId(row.getLong("client_id"));
        request.setMode(row.getString("mode"));
        request.setDeleted(row.getBoolean("deleted"));
        try {
            request.setConfig(objectMapper.readValue(row.getString("config"), RequestConfigModel.class));
        } catch (IOException e) {
            throw new ServerSideConflictException("request config can't be parsed, request=" + request, e);
        }
        return request;
    }
}
