package com.omgservers.service.shard.matchmaker.impl.operation.matchmakerMatchResource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceModel;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerMatchResourceCreatedEventBodyModel;
import com.omgservers.service.factory.system.LogModelFactory;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertMatchmakerMatchResourceOperationImpl implements UpsertMatchmakerMatchResourceOperation {

    final ChangeObjectOperation changeObjectOperation;

    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int slot,
                                final MatchmakerMatchResourceModel matchmakerMatchResource) {
        return changeObjectOperation.execute(
                changeContext, sqlConnection, slot,
                """
                        insert into $slot.tab_matchmaker_match_resource(
                            id, idempotency_key, matchmaker_id, created, modified, match_id, mode, status, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8, $9)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        matchmakerMatchResource.getId(),
                        matchmakerMatchResource.getIdempotencyKey(),
                        matchmakerMatchResource.getMatchmakerId(),
                        matchmakerMatchResource.getCreated().atOffset(ZoneOffset.UTC),
                        matchmakerMatchResource.getModified().atOffset(ZoneOffset.UTC),
                        matchmakerMatchResource.getMatchId(),
                        matchmakerMatchResource.getMode(),
                        matchmakerMatchResource.getStatus(),
                        matchmakerMatchResource.getDeleted()
                ),
                () -> new MatchmakerMatchResourceCreatedEventBodyModel(matchmakerMatchResource.getMatchmakerId(),
                        matchmakerMatchResource.getId()),
                () -> null
        );
    }
}
