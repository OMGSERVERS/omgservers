package com.omgservers.service.shard.tenant.impl.mapper;

import com.omgservers.schema.model.tenantLobbyResource.TenantLobbyResourceModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantLobbyResourceModelMapper {

    public TenantLobbyResourceModel fromRow(final Row row) {
        final var tenantLobbyResource = new TenantLobbyResourceModel();
        tenantLobbyResource.setId(row.getLong("id"));
        tenantLobbyResource.setTenantId(row.getLong("tenant_id"));
        tenantLobbyResource.setDeploymentId(row.getLong("deployment_id"));
        tenantLobbyResource.setCreated(row.getOffsetDateTime("created").toInstant());
        tenantLobbyResource.setModified(row.getOffsetDateTime("modified").toInstant());
        tenantLobbyResource.setIdempotencyKey(row.getString("idempotency_key"));
        tenantLobbyResource.setLobbyId(row.getLong("lobby_id"));
        tenantLobbyResource.setDeleted(row.getBoolean("deleted"));
        return tenantLobbyResource;
    }
}
