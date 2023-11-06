package com.omgservers.service.module.matchmaker.impl.operation.selectMatchClientsByMatchmakerId;

import com.omgservers.model.matchClient.MatchClientModel;
import com.omgservers.service.module.matchmaker.impl.mappers.MatchClientModelMapper;
import com.omgservers.service.operation.selectList.SelectListOperation;
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
class SelectMatchClientsByMatchmakerIdOperationImpl implements SelectMatchClientsByMatchmakerIdOperation {

    final SelectListOperation selectListOperation;

    final MatchClientModelMapper matchClientModelMapper;

    @Override
    public Uni<List<MatchClientModel>> selectMatchClientsByMatchmakerId(final SqlConnection sqlConnection,
                                                                        final int shard,
                                                                        final Long matchmakerId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select
                            id, matchmaker_id, match_id, created, modified,
                            user_id, client_id, group_name, config, deleted
                        from $schema.tab_matchmaker_match_client
                        where matchmaker_id = $1 and deleted = false
                        """,
                Collections.singletonList(matchmakerId),
                "Match client",
                matchClientModelMapper::fromRow);
    }
}
