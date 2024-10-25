package com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatchAssignment;

import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentModel;
import com.omgservers.service.module.matchmaker.impl.mappers.MatchmakerMatchAssignmentModelMapper;
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
class SelectMatchmakerMatchAssignmentOperationImpl implements SelectMatchmakerMatchAssignmentOperation {

    final SelectObjectOperation selectObjectOperation;

    final MatchmakerMatchAssignmentModelMapper matchmakerMatchAssignmentModelMapper;

    @Override
    public Uni<MatchmakerMatchAssignmentModel> execute(final SqlConnection sqlConnection,
                                                       final int shard,
                                                       final Long matchmakerId,
                                                       final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, matchmaker_id, match_id, created, modified, user_id, client_id, 
                            group_name, config, deleted
                        from $schema.tab_matchmaker_match_assignment
                        where matchmaker_id = $1 and id = $2
                        limit 1
                        """,
                List.of(
                        matchmakerId,
                        id
                ),
                "Matchmaker match client",
                matchmakerMatchAssignmentModelMapper::execute);
    }
}