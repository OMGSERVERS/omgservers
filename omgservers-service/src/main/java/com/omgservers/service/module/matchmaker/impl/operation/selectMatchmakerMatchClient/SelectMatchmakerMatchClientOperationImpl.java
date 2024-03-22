package com.omgservers.service.module.matchmaker.impl.operation.selectMatchmakerMatchClient;

import com.omgservers.model.matchmakerMatchClient.MatchmakerMatchClientModel;
import com.omgservers.service.module.matchmaker.impl.mappers.MatchClientModelMapper;
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
class SelectMatchmakerMatchClientOperationImpl implements SelectMatchmakerMatchClientOperation {

    final SelectObjectOperation selectObjectOperation;

    final MatchClientModelMapper matchClientModelMapper;

    @Override
    public Uni<MatchmakerMatchClientModel> selectMatchmakerMatchClient(final SqlConnection sqlConnection,
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
                        from $schema.tab_matchmaker_match_client
                        where matchmaker_id = $1 and id = $2
                        limit 1
                        """,
                List.of(
                        matchmakerId,
                        id
                ),
                "Matchmaker match client",
                matchClientModelMapper::fromRow);
    }
}
