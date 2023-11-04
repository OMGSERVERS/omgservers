package com.omgservers.service.module.matchmaker.impl.operation.selectMatchClientByMatchmakerIdAndClientId;

import com.omgservers.model.matchClient.MatchClientModel;
import com.omgservers.service.module.matchmaker.impl.mappers.MatchClientModelMapper;
import com.omgservers.service.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectMatchClientByMatchmakerIdAndClientIdOperationImpl
        implements SelectMatchClientByMatchmakerIdAndClientIdOperation {

    final SelectObjectOperation selectObjectOperation;

    final MatchClientModelMapper matchClientModelMapper;

    @Override
    public Uni<MatchClientModel> selectMatchClientByMatchmakerIdAndClientId(final SqlConnection sqlConnection,
                                                                            final int shard,
                                                                            final Long matchmakerId,
                                                                            final Long clientId) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, matchmaker_id, match_id, created, modified, user_id, client_id, group_name, config
                        from $schema.tab_matchmaker_match_client
                        where matchmaker_id = $1 and client_id = $2
                        limit 1
                        """,
                Arrays.asList(matchmakerId, clientId),
                "Match client",
                matchClientModelMapper::fromRow);
    }
}
