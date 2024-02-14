package com.omgservers.service.module.lobby.impl.mappers;

import com.omgservers.model.lobby.LobbyModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class LobbyModelMapper {

    public LobbyModel fromRow(Row row) {
        final var lobbyModel = new LobbyModel();
        lobbyModel.setId(row.getLong("id"));
        lobbyModel.setCreated(row.getOffsetDateTime("created").toInstant());
        lobbyModel.setModified(row.getOffsetDateTime("modified").toInstant());
        lobbyModel.setDeleted(row.getBoolean("deleted"));
        return lobbyModel;
    }
}
