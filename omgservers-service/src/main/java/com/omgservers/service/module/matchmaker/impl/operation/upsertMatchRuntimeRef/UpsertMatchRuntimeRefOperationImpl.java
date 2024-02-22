package com.omgservers.service.module.matchmaker.impl.operation.upsertMatchRuntimeRef;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.event.body.MatchRuntimeRefCreatedEventBodyModel;
import com.omgservers.model.matchRuntimeRef.MatchRuntimeRefModel;
import com.omgservers.service.factory.LogModelFactory;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
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
class UpsertMatchRuntimeRefOperationImpl implements UpsertMatchRuntimeRefOperation {

    final ChangeObjectOperation changeObjectOperation;

    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertMatchRuntimeRef(final ChangeContext<?> changeContext,
                                              final SqlConnection sqlConnection,
                                              final int shard,
                                              final MatchRuntimeRefModel matchRuntimeRef) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_matchmaker_match_runtime_ref(
                            id, matchmaker_id, match_id, created, modified, runtime_id, deleted)
                        values($1, $2, $3, $4, $5, $6, $7)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        matchRuntimeRef.getId(),
                        matchRuntimeRef.getMatchmakerId(),
                        matchRuntimeRef.getMatchId(),
                        matchRuntimeRef.getCreated().atOffset(ZoneOffset.UTC),
                        matchRuntimeRef.getModified().atOffset(ZoneOffset.UTC),
                        matchRuntimeRef.getRuntimeId(),
                        matchRuntimeRef.getDeleted()
                ),
                () -> new MatchRuntimeRefCreatedEventBodyModel(
                        matchRuntimeRef.getMatchmakerId(),
                        matchRuntimeRef.getMatchId(),
                        matchRuntimeRef.getId()),
                () -> null
        );
    }
}
