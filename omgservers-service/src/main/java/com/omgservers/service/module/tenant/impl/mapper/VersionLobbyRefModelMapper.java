package com.omgservers.service.module.tenant.impl.mapper;

import com.omgservers.model.versionLobbyRef.VersionLobbyRefModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class VersionLobbyRefModelMapper {

    public VersionLobbyRefModel fromRow(Row row) {
        final var versionLobbyRef = new VersionLobbyRefModel();
        versionLobbyRef.setId(row.getLong("id"));
        versionLobbyRef.setTenantId(row.getLong("tenant_id"));
        versionLobbyRef.setVersionId(row.getLong("version_id"));
        versionLobbyRef.setCreated(row.getOffsetDateTime("created").toInstant());
        versionLobbyRef.setModified(row.getOffsetDateTime("modified").toInstant());
        versionLobbyRef.setLobbyId(row.getLong("lobby_id"));
        versionLobbyRef.setDeleted(row.getBoolean("deleted"));
        return versionLobbyRef;
    }
}
