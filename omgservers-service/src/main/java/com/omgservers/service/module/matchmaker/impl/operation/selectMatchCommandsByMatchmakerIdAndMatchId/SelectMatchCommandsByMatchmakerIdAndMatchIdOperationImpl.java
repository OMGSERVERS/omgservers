package com.omgservers.service.module.matchmaker.impl.operation.selectMatchCommandsByMatchmakerIdAndMatchId;

import com.omgservers.model.matchCommand.MatchCommandModel;
import com.omgservers.service.module.matchmaker.impl.mappers.MatchCommandModelMapper;
import com.omgservers.service.operation.selectList.SelectListOperation;
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
class SelectMatchCommandsByMatchmakerIdAndMatchIdOperationImpl
        implements SelectMatchCommandsByMatchmakerIdAndMatchIdOperation {

    final SelectListOperation selectListOperation;

    final MatchCommandModelMapper matchCommandModelMapper;

    @Override
    public Uni<List<MatchCommandModel>> selectMatchCommandsByMatchmakerIdAndMatchId(
            final SqlConnection sqlConnection,
            final int shard,
            final Long matchmakerId,
            final Long matchId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select id, matchmaker_id, match_id, created, modified, qualifier, body, deleted
                        from $schema.tab_matchmaker_match_command
                        where matchmaker_id = $1 and match_id = $2 and deleted = false
                        order by id asc
                        """,
                Arrays.asList(matchmakerId, matchId),
                "Match command",
                matchCommandModelMapper::fromRow);
    }
}
