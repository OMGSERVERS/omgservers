package com.omgservers.service.module.matchmaker.impl.operation.matchmakerAssignment.selectActiveMatchmakerAssignmentsByMatchmakerId;

import com.omgservers.schema.model.matchmakerAssignment.MatchmakerAssignmentModel;
import com.omgservers.service.module.matchmaker.impl.mappers.MatchmakerAssignmentModelMapper;
import com.omgservers.service.server.operation.selectList.SelectListOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectActiveMatchmakerAssignmentsByMatchmakerIdOperationImpl
        implements SelectActiveMatchmakerAssignmentsByMatchmakerIdOperation {

    final SelectListOperation selectListOperation;

    final MatchmakerAssignmentModelMapper matchmakerAssignmentModelMapper;

    @Override
    public Uni<List<MatchmakerAssignmentModel>> selectActiveMatchmakerAssignmentsByMatchmakerId(
            final SqlConnection sqlConnection,
            final int shard,
            final Long matchmakerId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, matchmaker_id, created, modified, client_id, deleted
                        from $schema.tab_matchmaker_assignment
                        where matchmaker_id = $1 and deleted = false
                        order by id asc
                        """,
                Collections.singletonList(matchmakerId),
                "Matchmaker assignment",
                matchmakerAssignmentModelMapper::fromRow);
    }
}
