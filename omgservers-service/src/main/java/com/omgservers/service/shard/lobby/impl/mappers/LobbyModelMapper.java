package com.omgservers.service.shard.lobby.impl.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.lobby.LobbyConfigDto;
import com.omgservers.schema.model.lobby.LobbyModel;
import com.omgservers.service.exception.ServerSideConflictException;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class LobbyModelMapper {

    final ObjectMapper objectMapper;

    public LobbyModel fromRow(final Row row) {
        final var lobby = new LobbyModel();
        lobby.setId(row.getLong("id"));
        lobby.setIdempotencyKey(row.getString("idempotency_key"));
        lobby.setCreated(row.getOffsetDateTime("created").toInstant());
        lobby.setModified(row.getOffsetDateTime("modified").toInstant());
        lobby.setDeploymentId(row.getLong("deployment_id"));
        lobby.setRuntimeId(row.getLong("runtime_id"));
        lobby.setDeleted(row.getBoolean("deleted"));
        try {
            lobby.setConfig(objectMapper.readValue(row.getString("config"), LobbyConfigDto.class));
        } catch (IOException e) {
            throw new ServerSideConflictException(ExceptionQualifierEnum.DB_DATA_CORRUPTED,
                    "lobby config can't be parsed, lobby=" + lobby, e);
        }
        return lobby;
    }
}
