package com.omgservers.service.shard.matchmaker.impl.operation.matchmakerRequest;

import com.omgservers.schema.model.request.MatchmakerRequestModel;
import com.omgservers.service.shard.matchmaker.impl.mappers.MatchmakerRequestModelMapper;
import com.omgservers.service.operation.server.SelectListOperation;
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
class SelectActiveMatchmakerRequestsByMatchmakerIdOperationImpl
        implements SelectActiveMatchmakerRequestsByMatchmakerIdOperation {

    final SelectListOperation selectListOperation;

    final MatchmakerRequestModelMapper matchmakerRequestModelMapper;

    @Override
    public Uni<List<MatchmakerRequestModel>> execute(
            final SqlConnection sqlConnection,
            final int shard,
            final Long matchmakerId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, matchmaker_id, created, modified, user_id, client_id, mode, config, 
                            deleted
                        from $schema.tab_matchmaker_request
                        where matchmaker_id = $1 and deleted = false
                        order by id asc
                        """,
                Collections.singletonList(matchmakerId),
                "Matchmaker resource",
                matchmakerRequestModelMapper::execute);
    }
}
