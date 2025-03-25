package com.omgservers.service.shard.matchmaker.impl.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceModel;
import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceStatusEnum;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchmakerMatchResourceModelMapper {

    final ObjectMapper objectMapper;

    public MatchmakerMatchResourceModel execute(final Row row) {
        final var matchmakerMatchResource = new MatchmakerMatchResourceModel();
        matchmakerMatchResource.setId(row.getLong("id"));
        matchmakerMatchResource.setIdempotencyKey(row.getString("idempotency_key"));
        matchmakerMatchResource.setMatchmakerId(row.getLong("matchmaker_id"));
        matchmakerMatchResource.setCreated(row.getOffsetDateTime("created").toInstant());
        matchmakerMatchResource.setModified(row.getOffsetDateTime("modified").toInstant());
        matchmakerMatchResource.setMatchId(row.getLong("match_id"));
        matchmakerMatchResource.setMode(row.getString("mode"));
        matchmakerMatchResource.setStatus(MatchmakerMatchResourceStatusEnum.valueOf(row.getString("status")));
        matchmakerMatchResource.setDeleted(row.getBoolean("deleted"));
        return matchmakerMatchResource;
    }
}
