package com.omgservers.service.module.matchmaker.impl.operation.selectMatchmakerMatchClientByMatchmakerIdAndClientId;

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
class SelectMatchmakerMatchClientByMatchmakerIdAndClientIdOperationImpl
        implements SelectMatchmakerMatchClientByMatchmakerIdAndClientIdOperation {

    final SelectObjectOperation selectObjectOperation;

    final MatchClientModelMapper matchClientModelMapper;

    @Override
    public Uni<MatchmakerMatchClientModel> selectMatchmakerMatchClientByMatchmakerIdAndClientId(final SqlConnection sqlConnection,
                                                                                                final int shard,
                                                                                                final Long matchmakerId,
                                                                                                final Long clientId) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, matchmaker_id, match_id, created, modified, user_id, client_id, 
                            group_name, config, deleted
                        from $schema.tab_matchmaker_match_client
                        where matchmaker_id = $1 and client_id = $2
                        order by id desc
                        limit 1
                        """,
                List.of(matchmakerId, clientId),
                "Matchmaker match client",
                matchClientModelMapper::fromRow);
    }
}
