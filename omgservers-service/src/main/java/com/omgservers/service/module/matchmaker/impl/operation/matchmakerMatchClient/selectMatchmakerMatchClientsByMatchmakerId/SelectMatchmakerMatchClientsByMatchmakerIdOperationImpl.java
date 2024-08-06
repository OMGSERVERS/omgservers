package com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatchClient.selectMatchmakerMatchClientsByMatchmakerId;

import com.omgservers.schema.model.matchmakerMatchClient.MatchmakerMatchClientModel;
import com.omgservers.service.module.matchmaker.impl.mappers.MatchClientModelMapper;
import com.omgservers.service.server.operation.selectList.SelectListOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectMatchmakerMatchClientsByMatchmakerIdOperationImpl
        implements SelectMatchmakerMatchClientsByMatchmakerIdOperation {

    final SelectListOperation selectListOperation;

    final MatchClientModelMapper matchClientModelMapper;

    @Override
    public Uni<List<MatchmakerMatchClientModel>> selectMatchmakerMatchClientsByMatchmakerId(final SqlConnection sqlConnection,
                                                                                            final int shard,
                                                                                            final Long matchmakerId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, matchmaker_id, match_id, created, modified, user_id, client_id, 
                            group_name, config, deleted
                        from $schema.tab_matchmaker_match_client
                        where matchmaker_id = $1
                        order by id asc
                        """,
                Collections.singletonList(matchmakerId),
                "Matchmaker match client",
                matchClientModelMapper::fromRow);
    }
}
