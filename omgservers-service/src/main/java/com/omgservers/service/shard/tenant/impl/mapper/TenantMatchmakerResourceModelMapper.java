package com.omgservers.service.shard.tenant.impl.mapper;

import com.omgservers.schema.model.tenantMatchmakerResource.TenantMatchmakerResourceModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantMatchmakerResourceModelMapper {

    public TenantMatchmakerResourceModel fromRow(final Row row) {
        final var tenantMatchmakerResource = new TenantMatchmakerResourceModel();
        tenantMatchmakerResource.setId(row.getLong("id"));
        tenantMatchmakerResource.setTenantId(row.getLong("tenant_id"));
        tenantMatchmakerResource.setDeploymentId(row.getLong("deployment_id"));
        tenantMatchmakerResource.setCreated(row.getOffsetDateTime("created").toInstant());
        tenantMatchmakerResource.setModified(row.getOffsetDateTime("modified").toInstant());
        tenantMatchmakerResource.setIdempotencyKey(row.getString("idempotency_key"));
        tenantMatchmakerResource.setMatchmakerId(row.getLong("matchmaker_id"));
        tenantMatchmakerResource.setDeleted(row.getBoolean("deleted"));
        return tenantMatchmakerResource;
    }
}
