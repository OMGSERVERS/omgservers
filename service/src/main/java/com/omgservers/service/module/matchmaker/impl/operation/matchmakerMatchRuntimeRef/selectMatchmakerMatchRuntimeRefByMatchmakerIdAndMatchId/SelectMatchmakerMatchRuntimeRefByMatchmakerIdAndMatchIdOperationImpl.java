package com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatchRuntimeRef.selectMatchmakerMatchRuntimeRefByMatchmakerIdAndMatchId;

import com.omgservers.schema.model.matchmakerMatchRuntimeRef.MatchmakerMatchRuntimeRefModel;
import com.omgservers.service.module.matchmaker.impl.mappers.MatchmakerMatchRuntimeRefModelMapper;
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
class SelectMatchmakerMatchRuntimeRefByMatchmakerIdAndMatchIdOperationImpl
        implements SelectMatchmakerMatchRuntimeRefByMatchmakerIdAndMatchIdOperation {

    final SelectObjectOperation selectObjectOperation;

    final MatchmakerMatchRuntimeRefModelMapper matchmakerMatchRuntimeRefModelMapper;

    @Override
    public Uni<MatchmakerMatchRuntimeRefModel> selectMatchmakerMatchRuntimeRefByMatchmakerIdAndMatchId(
            final SqlConnection sqlConnection,
            final int shard,
            final Long matchmakerId,
            Long matchId) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, matchmaker_id, match_id, created, modified, runtime_id, deleted
                        from $schema.tab_matchmaker_match_runtime_ref
                        where matchmaker_id = $1 and match_id = $2
                        order by id desc
                        limit 1
                        """,
                List.of(
                        matchmakerId,
                        matchId),
                "Matchmaker match runtime ref",
                matchmakerMatchRuntimeRefModelMapper::fromRow);
    }
}
