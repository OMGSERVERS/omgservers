package com.omgservers.service.module.matchmaker.impl.operation.selectActiveMatchmakerMatchCommandsByMatchId;

import com.omgservers.model.matchCommand.MatchmakerMatchCommandModel;
import com.omgservers.service.module.matchmaker.impl.mappers.MatchCommandModelMapper;
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
class SelectActiveMatchmakerMatchCommandsByMatchIdOperationImpl
        implements SelectActiveMatchmakerMatchCommandsByMatchIdOperation {

    final SelectListOperation selectListOperation;

    final MatchCommandModelMapper matchCommandModelMapper;

    @Override
    public Uni<List<MatchmakerMatchCommandModel>> selectActiveMatchmakerMatchCommandsByMatchId(
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
                List.of(
                        matchmakerId,
                        matchId
                ),
                "Matchmaker match command",
                matchCommandModelMapper::fromRow);
    }
}
