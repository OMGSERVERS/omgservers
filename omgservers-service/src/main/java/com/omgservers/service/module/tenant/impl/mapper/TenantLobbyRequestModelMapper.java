package com.omgservers.service.module.tenant.impl.mapper;

import com.omgservers.schema.model.tenantLobbyRequest.TenantLobbyRequestModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantLobbyRequestModelMapper {

    public TenantLobbyRequestModel fromRow(final Row row) {
        final var versionLobbyRequest = new TenantLobbyRequestModel();
        versionLobbyRequest.setId(row.getLong("id"));
        versionLobbyRequest.setTenantId(row.getLong("tenant_id"));
        versionLobbyRequest.setDeploymentId(row.getLong("deployment_id"));
        versionLobbyRequest.setCreated(row.getOffsetDateTime("created").toInstant());
        versionLobbyRequest.setModified(row.getOffsetDateTime("modified").toInstant());
        versionLobbyRequest.setIdempotencyKey(row.getString("idempotency_key"));
        versionLobbyRequest.setLobbyId(row.getLong("lobby_id"));
        versionLobbyRequest.setDeleted(row.getBoolean("deleted"));
        return versionLobbyRequest;
    }
}
