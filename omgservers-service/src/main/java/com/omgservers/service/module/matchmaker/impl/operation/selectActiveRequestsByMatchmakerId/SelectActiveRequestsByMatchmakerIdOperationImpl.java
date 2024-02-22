package com.omgservers.service.module.matchmaker.impl.operation.selectActiveRequestsByMatchmakerId;

import com.omgservers.model.request.RequestModel;
import com.omgservers.service.module.matchmaker.impl.mappers.RequestModelMapper;
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
class SelectActiveRequestsByMatchmakerIdOperationImpl implements SelectActiveRequestsByMatchmakerIdOperation {

    final SelectListOperation selectListOperation;

    final RequestModelMapper requestModelMapper;

    @Override
    public Uni<List<RequestModel>> selectActiveRequestsByMatchmakerId(final SqlConnection sqlConnection,
                                                                      final int shard,
                                                                      final Long matchmakerId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select id, matchmaker_id, created, modified, user_id, client_id, mode, config, deleted
                        from $schema.tab_matchmaker_request
                        where matchmaker_id = $1 and deleted = false
                        order by id asc
                        """,
                Collections.singletonList(matchmakerId),
                "Request",
                requestModelMapper::fromRow);
    }
}
