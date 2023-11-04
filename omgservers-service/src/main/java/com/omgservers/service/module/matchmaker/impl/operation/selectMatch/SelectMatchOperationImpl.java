package com.omgservers.service.module.matchmaker.impl.operation.selectMatch;

import com.omgservers.model.match.MatchModel;
import com.omgservers.service.module.matchmaker.impl.mappers.MatchModelMapper;
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
class SelectMatchOperationImpl implements SelectMatchOperation {

    final SelectObjectOperation selectObjectOperation;

    final MatchModelMapper matchModelMapper;

    @Override
    public Uni<MatchModel> selectMatch(final SqlConnection sqlConnection,
                                       final int shard,
                                       final Long matchmakerId,
                                       final Long id,
                                       final Boolean deleted) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, matchmaker_id, created, modified, runtime_id, stopped, config, deleted
                        from $schema.tab_matchmaker_match
                        where matchmaker_id = $1 and id = $2 and deleted = $3
                        limit 1
                        """,
                Arrays.asList(matchmakerId, id, deleted),
                "Match",
                matchModelMapper::fromRow);
    }
}
