package com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatchClient.selectActiveMatchmakerMatchClientsByMatchId;

import com.omgservers.schema.model.matchmakerMatchClient.MatchmakerMatchClientModel;
import com.omgservers.service.module.matchmaker.impl.mappers.MatchClientModelMapper;
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
class SelectActiveMatchmakerMatchClientsByMatchIdOperationImpl
        implements SelectActiveMatchmakerMatchClientsByMatchIdOperation {

    final SelectListOperation selectListOperation;

    final MatchClientModelMapper matchClientModelMapper;

    @Override
    public Uni<List<MatchmakerMatchClientModel>> selectActiveMatchmakerMatchClientsByMatchId(final SqlConnection sqlConnection,
                                                                                             final int shard,
                                                                                             final Long matchmakerId,
                                                                                             final Long matchId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, matchmaker_id, match_id, created, modified, user_id, client_id, 
                            group_name, config, deleted
                        from $schema.tab_matchmaker_match_client
                        where matchmaker_id = $1 and match_id = $2 and deleted = false
                        order by id asc
                        """,
                List.of(matchmakerId, matchId),
                "Matchmaker match client",
                matchClientModelMapper::fromRow);
    }
}
