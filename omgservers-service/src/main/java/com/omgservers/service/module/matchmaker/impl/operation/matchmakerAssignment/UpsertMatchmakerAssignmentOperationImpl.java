package com.omgservers.service.module.matchmaker.impl.operation.matchmakerAssignment;

import com.omgservers.service.event.body.module.matchmaker.MatchmakerAssignmentCreatedEventBodyModel;
import com.omgservers.schema.model.matchmakerAssignment.MatchmakerAssignmentModel;
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
class UpsertMatchmakerAssignmentOperationImpl implements UpsertMatchmakerAssignmentOperation {

    final ChangeObjectOperation changeObjectOperation;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int shard,
                                final MatchmakerAssignmentModel matchmakerAssignment) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_matchmaker_assignment(
                            id, idempotency_key, matchmaker_id, created, modified, client_id, deleted)
                        values($1, $2, $3, $4, $5, $6, $7)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        matchmakerAssignment.getId(),
                        matchmakerAssignment.getIdempotencyKey(),
                        matchmakerAssignment.getMatchmakerId(),
                        matchmakerAssignment.getCreated().atOffset(ZoneOffset.UTC),
                        matchmakerAssignment.getModified().atOffset(ZoneOffset.UTC),
                        matchmakerAssignment.getClientId(),
                        matchmakerAssignment.getDeleted()
                ),
                () -> new MatchmakerAssignmentCreatedEventBodyModel(matchmakerAssignment.getMatchmakerId(),
                        matchmakerAssignment.getId()),
                () -> null
        );
    }
}
