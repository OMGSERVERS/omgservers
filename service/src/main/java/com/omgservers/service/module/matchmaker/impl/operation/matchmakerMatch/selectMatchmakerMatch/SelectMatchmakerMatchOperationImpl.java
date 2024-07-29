package com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatch.selectMatchmakerMatch;

import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.service.module.matchmaker.impl.mappers.MatchmakerMatchModelMapper;
import com.omgservers.service.server.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectMatchmakerMatchOperationImpl implements SelectMatchmakerMatchOperation {

    final SelectObjectOperation selectObjectOperation;

    final MatchmakerMatchModelMapper matchmakerMatchModelMapper;

    @Override
    public Uni<MatchmakerMatchModel> selectMatchmakerMatch(final SqlConnection sqlConnection,
                                                           final int shard,
                                                           final Long matchmakerId,
                                                           final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, matchmaker_id, created, modified, runtime_id, config, status, deleted
                        from $schema.tab_matchmaker_match
                        where matchmaker_id = $1 and id = $2
                        limit 1
                        """,
                List.of(matchmakerId, id),
                "Matchmaker match",
                matchmakerMatchModelMapper::fromRow);
    }
}
