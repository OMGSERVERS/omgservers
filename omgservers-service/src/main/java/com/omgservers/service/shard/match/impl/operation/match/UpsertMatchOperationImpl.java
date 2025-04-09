package com.omgservers.service.shard.match.impl.operation.match;

import com.omgservers.schema.model.match.MatchModel;
import com.omgservers.service.event.body.module.match.MatchCreatedEventBodyModel;
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
class UpsertMatchOperationImpl implements UpsertMatchOperation {

    final ChangeObjectOperation changeObjectOperation;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int shard,
                                final MatchModel match) {
        return changeObjectOperation.execute(
                changeContext, sqlConnection, shard,
                """
                        insert into $shard.tab_match(
                            id, idempotency_key, created, modified, matchmaker_id, runtime_id, deleted)
                        values($1, $2, $3, $4, $5, $6, $7)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        match.getId(),
                        match.getIdempotencyKey(),
                        match.getCreated().atOffset(ZoneOffset.UTC),
                        match.getModified().atOffset(ZoneOffset.UTC),
                        match.getMatchmakerId(),
                        match.getRuntimeId(),
                        match.getDeleted()
                ),
                () -> new MatchCreatedEventBodyModel(match.getId()),
                () -> null
        );
    }
}
