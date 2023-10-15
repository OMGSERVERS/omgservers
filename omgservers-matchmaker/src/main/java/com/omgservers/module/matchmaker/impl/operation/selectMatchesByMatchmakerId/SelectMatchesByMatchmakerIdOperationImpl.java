package com.omgservers.module.matchmaker.impl.operation.selectMatchesByMatchmakerId;

import com.omgservers.model.match.MatchModel;
import com.omgservers.module.matchmaker.impl.mappers.MatchModelMapper;
import com.omgservers.operation.selectList.SelectListOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectMatchesByMatchmakerIdOperationImpl implements SelectMatchesByMatchmakerIdOperation {

    final SelectListOperation selectListOperation;

    final MatchModelMapper matchModelMapper;

    @Override
    public Uni<List<MatchModel>> selectMatchesByMatchmakerId(final SqlConnection sqlConnection,
                                                             final int shard,
                                                             final Long matchmakerId,
                                                             final Boolean deleted) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select id, matchmaker_id, created, modified, runtime_id, stopped, config, deleted
                        from $schema.tab_matchmaker_match
                        where matchmaker_id = $1 and deleted = $2
                        """,
                Arrays.asList(matchmakerId, deleted),
                "Match",
                matchModelMapper::fromRow);
    }
}
