package com.omgservers.service.module.tenant.impl.mapper;

import com.omgservers.schema.model.tenantMatchmakerRef.TenantMatchmakerRefModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantMatchmakerRefModelMapper {

    public TenantMatchmakerRefModel fromRow(final Row row) {
        final var versionMatchmakerRef = new TenantMatchmakerRefModel();
        versionMatchmakerRef.setId(row.getLong("id"));
        versionMatchmakerRef.setTenantId(row.getLong("tenant_id"));
        versionMatchmakerRef.setDeploymentId(row.getLong("deployment_id"));
        versionMatchmakerRef.setCreated(row.getOffsetDateTime("created").toInstant());
        versionMatchmakerRef.setModified(row.getOffsetDateTime("modified").toInstant());
        versionMatchmakerRef.setIdempotencyKey(row.getString("idempotency_key"));
        versionMatchmakerRef.setMatchmakerId(row.getLong("matchmaker_id"));
        versionMatchmakerRef.setDeleted(row.getBoolean("deleted"));
        return versionMatchmakerRef;
    }
}
