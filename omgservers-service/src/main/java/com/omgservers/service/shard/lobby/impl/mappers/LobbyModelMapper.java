package com.omgservers.service.shard.lobby.impl.mappers;

import com.omgservers.schema.model.lobby.LobbyModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class LobbyModelMapper {

    public LobbyModel fromRow(final Row row) {
        final var lobby = new LobbyModel();
        lobby.setId(row.getLong("id"));
        lobby.setIdempotencyKey(row.getString("idempotency_key"));
        lobby.setCreated(row.getOffsetDateTime("created").toInstant());
        lobby.setModified(row.getOffsetDateTime("modified").toInstant());
        lobby.setTenantId(row.getLong("tenant_id"));
        lobby.setDeploymentId(row.getLong("deployment_id"));
        lobby.setRuntimeId(row.getLong("runtime_id"));
        lobby.setDeleted(row.getBoolean("deleted"));
        return lobby;
    }
}
