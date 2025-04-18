package com.omgservers.service.shard.matchmaker.impl.operation.matchmakerRequest;

import com.omgservers.schema.model.matchmakerRequest.MatchmakerRequestModel;
import com.omgservers.service.operation.server.SelectListOperation;
import com.omgservers.service.shard.matchmaker.impl.mappers.MatchmakerRequestModelMapper;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectActiveMatchmakerRequestsByMatchmakerIdOperationImpl
        implements SelectActiveMatchmakerRequestsByMatchmakerIdOperation {

    final SelectListOperation selectListOperation;

    final MatchmakerRequestModelMapper matchmakerRequestModelMapper;

    @Override
    public Uni<List<MatchmakerRequestModel>> execute(final SqlConnection sqlConnection,
                                                     final int slot,
                                                     final Long matchmakerId) {
        return selectListOperation.selectList(
                sqlConnection,
                slot,
                """
                        select
                            id, idempotency_key, matchmaker_id, created, modified, client_id, mode, config, deleted
                        from $slot.tab_matchmaker_request
                        where matchmaker_id = $1 and deleted = false
                        order by id asc
                        """,
                List.of(matchmakerId),
                "Matchmaker request",
                matchmakerRequestModelMapper::execute);
    }
}
