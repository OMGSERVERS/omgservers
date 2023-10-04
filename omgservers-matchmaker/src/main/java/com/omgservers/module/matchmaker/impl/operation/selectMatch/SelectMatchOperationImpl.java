package com.omgservers.module.matchmaker.impl.operation.selectMatch;

import com.omgservers.model.match.MatchModel;
import com.omgservers.module.matchmaker.impl.mappers.MatchModelMapper;
import com.omgservers.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectMatchOperationImpl implements SelectMatchOperation {

    final SelectObjectOperation selectObjectOperation;

    final MatchModelMapper matchModelMapper;

    @Override
    public Uni<MatchModel> selectMatch(final SqlConnection sqlConnection,
                                       final int shard,
                                       final Long matchmakerId,
                                       final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, matchmaker_id, created, modified, runtime_id, config
                        from $schema.tab_matchmaker_match
                        where matchmaker_id = $1 and id = $2
                        limit 1
                        """,
                Arrays.asList(matchmakerId, id),
                "Match",
                matchModelMapper::fromRow);
    }
}
