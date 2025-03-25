package com.omgservers.service.shard.match.impl.mappers;

import com.omgservers.schema.model.match.MatchModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchModelMapper {

    public MatchModel execute(final Row row) {
        final var match = new MatchModel();
        match.setId(row.getLong("id"));
        match.setIdempotencyKey(row.getString("idempotency_key"));
        match.setCreated(row.getOffsetDateTime("created").toInstant());
        match.setModified(row.getOffsetDateTime("modified").toInstant());
        match.setMatchmakerId(row.getLong("matchmaker_id"));
        match.setRuntimeId(row.getLong("runtime_id"));
        match.setDeleted(row.getBoolean("deleted"));
        return match;
    }
}
