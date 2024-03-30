package com.omgservers.service.module.matchmaker.impl.operation.matchmakerRequest.selectActiveMatchmakerRequestsByMatchmakerId;

import com.omgservers.model.request.MatchmakerRequestModel;
import com.omgservers.service.module.matchmaker.impl.mappers.MatchmakerRequestModelMapper;
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
class SelectActiveMatchmakerRequestsByMatchmakerIdOperationImpl
        implements SelectActiveMatchmakerRequestsByMatchmakerIdOperation {

    final SelectListOperation selectListOperation;

    final MatchmakerRequestModelMapper matchmakerRequestModelMapper;

    @Override
    public Uni<List<MatchmakerRequestModel>> selectActiveMatchmakerRequestsByMatchmakerId(
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
                "Matchmaker request",
                matchmakerRequestModelMapper::fromRow);
    }
}
