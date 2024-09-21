package com.omgservers.service.module.tenant.impl.mapper;

import com.omgservers.schema.model.tenantMatchmakerRequest.TenantMatchmakerRequestModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantMatchmakerRequestModelMapper {

    public TenantMatchmakerRequestModel fromRow(final Row row) {
        final var VersionMatchmakerRequest = new TenantMatchmakerRequestModel();
        VersionMatchmakerRequest.setId(row.getLong("id"));
        VersionMatchmakerRequest.setTenantId(row.getLong("tenant_id"));
        VersionMatchmakerRequest.setDeploymentId(row.getLong("deployment_id"));
        VersionMatchmakerRequest.setCreated(row.getOffsetDateTime("created").toInstant());
        VersionMatchmakerRequest.setModified(row.getOffsetDateTime("modified").toInstant());
        VersionMatchmakerRequest.setIdempotencyKey(row.getString("idempotency_key"));
        VersionMatchmakerRequest.setMatchmakerId(row.getLong("matchmaker_id"));
        VersionMatchmakerRequest.setDeleted(row.getBoolean("deleted"));
        return VersionMatchmakerRequest;
    }
}
