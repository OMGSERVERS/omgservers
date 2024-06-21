package com.omgservers.service.module.lobby.impl.mappers;

import com.omgservers.model.lobbyRuntimeRef.LobbyRuntimeRefModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class LobbyRuntimeRefModelMapper {

    public LobbyRuntimeRefModel fromRow(final Row row) {
        final var lobbyRuntimeRef = new LobbyRuntimeRefModel();
        lobbyRuntimeRef.setId(row.getLong("id"));
        lobbyRuntimeRef.setIdempotencyKey(row.getString("idempotency_key"));
        lobbyRuntimeRef.setLobbyId(row.getLong("lobby_id"));
        lobbyRuntimeRef.setCreated(row.getOffsetDateTime("created").toInstant());
        lobbyRuntimeRef.setModified(row.getOffsetDateTime("modified").toInstant());
        lobbyRuntimeRef.setRuntimeId(row.getLong("runtime_id"));
        lobbyRuntimeRef.setDeleted(row.getBoolean("deleted"));
        return lobbyRuntimeRef;
    }
}
