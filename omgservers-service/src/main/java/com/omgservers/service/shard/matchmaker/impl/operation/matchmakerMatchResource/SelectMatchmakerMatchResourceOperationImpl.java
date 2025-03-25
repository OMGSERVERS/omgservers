package com.omgservers.service.shard.matchmaker.impl.operation.matchmakerMatchResource;

import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceModel;
import com.omgservers.service.operation.server.SelectObjectOperation;
import com.omgservers.service.shard.matchmaker.impl.mappers.MatchmakerMatchResourceModelMapper;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectMatchmakerMatchResourceOperationImpl implements SelectMatchmakerMatchResourceOperation {

    final SelectObjectOperation selectObjectOperation;

    final MatchmakerMatchResourceModelMapper matchmakerMatchResourceModelMapper;

    @Override
    public Uni<MatchmakerMatchResourceModel> execute(final SqlConnection sqlConnection,
                                                     final int shard,
                                                     final Long matchmakerId,
                                                     final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, matchmaker_id, created, modified, match_id, mode, status, deleted
                        from $schema.tab_matchmaker_match_resource
                        where matchmaker_id = $1 and id = $2
                        limit 1
                        """,
                List.of(matchmakerId, id),
                "Matchmaker match resource",
                matchmakerMatchResourceModelMapper::execute);
    }
}
