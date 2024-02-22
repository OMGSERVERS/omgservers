package com.omgservers.service.module.matchmaker.impl.operation.selectMatchRuntimeRef;

import com.omgservers.model.matchRuntimeRef.MatchRuntimeRefModel;
import com.omgservers.service.module.matchmaker.impl.mappers.MatchRuntimeRefModelMapper;
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
class SelectMatchRuntimeRefOperationImpl implements SelectMatchRuntimeRefOperation {

    final SelectObjectOperation selectObjectOperation;

    final MatchRuntimeRefModelMapper matchRuntimeRefModelMapper;

    @Override
    public Uni<MatchRuntimeRefModel> selectMatchRuntimeRef(final SqlConnection sqlConnection,
                                                           final int shard,
                                                           final Long matchmakerId,
                                                           final Long matchId,
                                                           final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select
                            id, matchmaker_id, match_id, created, modified, runtime_id, deleted
                        from $schema.tab_matchmaker_match_runtime_ref
                        where matchmaker_id = $1 and match_id = $2 and id = $3
                        limit 1
                        """,
                List.of(
                        matchmakerId,
                        matchId,
                        id
                ),
                "Match runtime ref",
                matchRuntimeRefModelMapper::fromRow);
    }
}
