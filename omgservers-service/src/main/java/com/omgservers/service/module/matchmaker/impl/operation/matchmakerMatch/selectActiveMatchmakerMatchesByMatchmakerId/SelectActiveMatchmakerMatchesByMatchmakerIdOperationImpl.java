package com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatch.selectActiveMatchmakerMatchesByMatchmakerId;

import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.service.module.matchmaker.impl.mappers.MatchmakerMatchModelMapper;
import com.omgservers.service.operation.selectList.SelectListOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectActiveMatchmakerMatchesByMatchmakerIdOperationImpl
        implements SelectActiveMatchmakerMatchesByMatchmakerIdOperation {

    final SelectListOperation selectListOperation;

    final MatchmakerMatchModelMapper matchmakerMatchModelMapper;

    @Override
    public Uni<List<MatchmakerMatchModel>> selectActiveMatchmakerMatchesByMatchmakerId(
            final SqlConnection sqlConnection,
            final int shard,
            final Long matchmakerId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, matchmaker_id, created, modified, runtime_id, config, status, deleted
                        from $schema.tab_matchmaker_match
                        where matchmaker_id = $1 and deleted = false
                        order by id asc
                        """,
                List.of(matchmakerId),
                "Matchmaker match",
                matchmakerMatchModelMapper::fromRow);
    }
}
