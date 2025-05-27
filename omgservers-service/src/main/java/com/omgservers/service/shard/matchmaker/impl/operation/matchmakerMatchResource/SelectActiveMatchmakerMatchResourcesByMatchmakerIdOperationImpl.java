package com.omgservers.service.shard.matchmaker.impl.operation.matchmakerMatchResource;

import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceModel;
import com.omgservers.service.operation.server.SelectListOperation;
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
class SelectActiveMatchmakerMatchResourcesByMatchmakerIdOperationImpl
        implements SelectActiveMatchmakerMatchResourcesByMatchmakerIdOperation {

    final SelectListOperation selectListOperation;

    final MatchmakerMatchResourceModelMapper matchmakerMatchResourceModelMapper;

    @Override
    public Uni<List<MatchmakerMatchResourceModel>> execute(final SqlConnection sqlConnection,
                                                           final int slot,
                                                           final Long matchmakerId) {
        return selectListOperation.selectList(
                sqlConnection,
                slot,
                """
                        select
                            id, idempotency_key, matchmaker_id, created, modified, match_id, status, config, deleted
                        from $slot.tab_matchmaker_match_resource
                        where matchmaker_id = $1 and deleted = false
                        order by id asc
                        """,
                List.of(matchmakerId),
                "Matchmaker match resource",
                matchmakerMatchResourceModelMapper::execute);
    }
}
