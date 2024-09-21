package com.omgservers.service.module.tenant.impl.mapper;

import com.omgservers.schema.model.tenantLobbyRef.TenantLobbyRefModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantLobbyRefModelMapper {

    public TenantLobbyRefModel fromRow(final Row row) {
        final var tenantLobbyRef = new TenantLobbyRefModel();
        tenantLobbyRef.setId(row.getLong("id"));
        tenantLobbyRef.setTenantId(row.getLong("tenant_id"));
        tenantLobbyRef.setDeploymentId(row.getLong("deployment_id"));
        tenantLobbyRef.setCreated(row.getOffsetDateTime("created").toInstant());
        tenantLobbyRef.setModified(row.getOffsetDateTime("modified").toInstant());
        tenantLobbyRef.setIdempotencyKey(row.getString("idempotency_key"));
        tenantLobbyRef.setLobbyId(row.getLong("lobby_id"));
        tenantLobbyRef.setDeleted(row.getBoolean("deleted"));
        return tenantLobbyRef;
    }
}
