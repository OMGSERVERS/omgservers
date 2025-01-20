package com.omgservers.service.shard.matchmaker.impl.mappers;

import com.omgservers.schema.model.matchmaker.MatchmakerModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchmakerModelMapper {

    public MatchmakerModel execute(final Row row) {
        final var matchmaker = new MatchmakerModel();
        matchmaker.setId(row.getLong("id"));
        matchmaker.setIdempotencyKey(row.getString("idempotency_key"));
        matchmaker.setCreated(row.getOffsetDateTime("created").toInstant());
        matchmaker.setModified(row.getOffsetDateTime("modified").toInstant());
        matchmaker.setTenantId(row.getLong("tenant_id"));
        matchmaker.setDeploymentId(row.getLong("deployment_id"));
        matchmaker.setDeleted(row.getBoolean("deleted"));
        return matchmaker;
    }
}
