package com.omgservers.service.shard.matchmaker.impl.operation.matchmakerMatchRuntimeRef;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.matchmakerMatchRuntimeRef.MatchmakerMatchRuntimeRefModel;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerMatchRuntimeRefCreatedEventBodyModel;
import com.omgservers.service.factory.lobby.LogModelFactory;
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
class UpsertMatchmakerMatchRuntimeRefOperationImpl implements UpsertMatchmakerMatchRuntimeRefOperation {

    final ChangeObjectOperation changeObjectOperation;

    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int shard,
                                final MatchmakerMatchRuntimeRefModel matchRuntimeRef) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_matchmaker_match_runtime_ref(
                            id, idempotency_key, matchmaker_id, match_id, created, modified, runtime_id, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        matchRuntimeRef.getId(),
                        matchRuntimeRef.getIdempotencyKey(),
                        matchRuntimeRef.getMatchmakerId(),
                        matchRuntimeRef.getMatchId(),
                        matchRuntimeRef.getCreated().atOffset(ZoneOffset.UTC),
                        matchRuntimeRef.getModified().atOffset(ZoneOffset.UTC),
                        matchRuntimeRef.getRuntimeId(),
                        matchRuntimeRef.getDeleted()
                ),
                () -> new MatchmakerMatchRuntimeRefCreatedEventBodyModel(
                        matchRuntimeRef.getMatchmakerId(),
                        matchRuntimeRef.getMatchId(),
                        matchRuntimeRef.getId()),
                () -> null
        );
    }
}
