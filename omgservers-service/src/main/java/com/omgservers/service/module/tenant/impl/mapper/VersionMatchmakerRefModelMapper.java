package com.omgservers.service.module.tenant.impl.mapper;

import com.omgservers.model.versionMatchmakerRef.VersionMatchmakerRefModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class VersionMatchmakerRefModelMapper {

    public VersionMatchmakerRefModel fromRow(Row row) {
        final var versionMatchmakerRef = new VersionMatchmakerRefModel();
        versionMatchmakerRef.setId(row.getLong("id"));
        versionMatchmakerRef.setTenantId(row.getLong("tenant_id"));
        versionMatchmakerRef.setVersionId(row.getLong("version_id"));
        versionMatchmakerRef.setCreated(row.getOffsetDateTime("created").toInstant());
        versionMatchmakerRef.setModified(row.getOffsetDateTime("modified").toInstant());
        versionMatchmakerRef.setIdempotencyKey(row.getString("idempotency_key"));
        versionMatchmakerRef.setMatchmakerId(row.getLong("matchmaker_id"));
        versionMatchmakerRef.setDeleted(row.getBoolean("deleted"));
        return versionMatchmakerRef;
    }
}
