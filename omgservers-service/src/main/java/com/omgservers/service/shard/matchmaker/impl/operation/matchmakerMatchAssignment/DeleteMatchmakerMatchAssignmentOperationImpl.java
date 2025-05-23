package com.omgservers.service.shard.matchmaker.impl.operation.matchmakerMatchAssignment;

import com.omgservers.service.event.body.module.matchmaker.MatchmakerMatchAssignmentDeletedEventBodyModel;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteMatchmakerMatchAssignmentOperationImpl implements DeleteMatchmakerMatchAssignmentOperation {

    final SelectMatchmakerMatchAssignmentOperation selectMatchmakerMatchAssignmentOperation;
    final ChangeObjectOperation changeObjectOperation;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int slot,
                                final Long matchmakerId,
                                final Long id) {
        return changeObjectOperation.execute(
                changeContext, sqlConnection, slot,
                """
                        update $slot.tab_matchmaker_match_assignment
                        set modified = $3, deleted = true
                        where matchmaker_id = $1 and id = $2 and deleted = false
                        """,
                List.of(
                        matchmakerId,
                        id,
                        Instant.now().atOffset(ZoneOffset.UTC)
                ),
                () -> new MatchmakerMatchAssignmentDeletedEventBodyModel(matchmakerId, id),
                () -> null
        );
    }
}
