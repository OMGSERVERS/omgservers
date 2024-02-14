package com.omgservers.service.module.lobby.impl.mappers;

import com.omgservers.model.lobbyRuntime.LobbyRuntimeModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class LobbyRuntimeModelMapper {

    public LobbyRuntimeModel fromRow(Row row) {
        final var lobbyRuntimeModel = new LobbyRuntimeModel();
        lobbyRuntimeModel.setId(row.getLong("id"));
        lobbyRuntimeModel.setLobbyId(row.getLong("lobby_id"));
        lobbyRuntimeModel.setCreated(row.getOffsetDateTime("created").toInstant());
        lobbyRuntimeModel.setModified(row.getOffsetDateTime("modified").toInstant());
        lobbyRuntimeModel.setRuntimeId(row.getLong("runtime_id"));
        lobbyRuntimeModel.setDeleted(row.getBoolean("deleted"));
        return lobbyRuntimeModel;
    }
}
