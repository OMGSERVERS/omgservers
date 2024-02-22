package com.omgservers.service.module.matchmaker.impl.operation.selectActiveMatchClientsByMatchId;

import com.omgservers.model.matchClient.MatchClientModel;
import com.omgservers.service.module.matchmaker.impl.mappers.MatchClientModelMapper;
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
class SelectActiveMatchClientsByMatchIdOperationImpl
        implements SelectActiveMatchClientsByMatchIdOperation {

    final SelectListOperation selectListOperation;

    final MatchClientModelMapper matchClientModelMapper;

    @Override
    public Uni<List<MatchClientModel>> selectActiveMatchClientsByMatchId(final SqlConnection sqlConnection,
                                                                         final int shard,
                                                                         final Long matchmakerId,
                                                                         final Long matchId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select
                            id, matchmaker_id, match_id, created, modified,
                            user_id, client_id, group_name, config, deleted
                        from $schema.tab_matchmaker_match_client
                        where matchmaker_id = $1 and match_id = $2 and deleted = false
                        order by id asc
                        """,
                Arrays.asList(matchmakerId, matchId),
                "Match client",
                matchClientModelMapper::fromRow);
    }
}
