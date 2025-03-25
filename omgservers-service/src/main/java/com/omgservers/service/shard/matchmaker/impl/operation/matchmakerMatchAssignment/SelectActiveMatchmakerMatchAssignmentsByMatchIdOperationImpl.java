package com.omgservers.service.shard.matchmaker.impl.operation.matchmakerMatchAssignment;

import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentModel;
import com.omgservers.service.shard.matchmaker.impl.mappers.MatchmakerMatchAssignmentModelMapper;
import com.omgservers.service.operation.server.SelectListOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectActiveMatchmakerMatchAssignmentsByMatchIdOperationImpl
        implements SelectActiveMatchmakerMatchAssignmentsByMatchIdOperation {

    final SelectListOperation selectListOperation;

    final MatchmakerMatchAssignmentModelMapper matchmakerMatchAssignmentModelMapper;

    @Override
    public Uni<List<MatchmakerMatchAssignmentModel>> execute(final SqlConnection sqlConnection,
                                                             final int shard,
                                                             final Long matchmakerId,
                                                             final Long matchId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, matchmaker_id, created, modified, match_id, client_id,
                            group_name, config, deleted
                        from $schema.tab_matchmaker_match_assignment
                        where matchmaker_id = $1 and match_id = $2 and deleted = false
                        order by id asc
                        """,
                List.of(matchmakerId, matchId),
                "Matchmaker match assignment",
                matchmakerMatchAssignmentModelMapper::execute);
    }
}
