package com.omgservers.service.module.matchmaker.impl.operation.matchmakerAssignment.selectMatchmakerAssignmentByMatchmakerIdAndClientId;

import com.omgservers.schema.model.matchmakerAssignment.MatchmakerAssignmentModel;
import com.omgservers.service.module.matchmaker.impl.mappers.MatchmakerAssignmentModelMapper;
import com.omgservers.service.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectMatchmakerAssignmentByMatchmakerIdAndClientIdOperationImpl
        implements SelectMatchmakerAssignmentByMatchmakerIdAndClientIdOperation {

    final SelectObjectOperation selectObjectOperation;

    final MatchmakerAssignmentModelMapper matchmakerAssignmentModelMapper;

    @Override
    public Uni<MatchmakerAssignmentModel> selectMatchmakerAssignmentByMatchmakerIdAndClientId(
            final SqlConnection sqlConnection,
            final int shard,
            final Long matchmakerId,
            final Long clientId) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, matchmaker_id, created, modified, client_id, deleted
                        from $schema.tab_matchmaker_assignment
                        where matchmaker_id = $1 and client_id = $2
                        order by id desc
                        limit 1
                        """,
                List.of(matchmakerId, clientId),
                "Matchmaker assignment",
                matchmakerAssignmentModelMapper::fromRow);
    }
}
