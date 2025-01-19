package com.omgservers.service.module.matchmaker.impl.operation.matchmakerRequest;

import com.omgservers.schema.model.request.MatchmakerRequestModel;
import com.omgservers.service.module.matchmaker.impl.mappers.MatchmakerRequestModelMapper;
import com.omgservers.service.operation.server.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectMatchmakerRequestOperationImpl implements SelectMatchmakerRequestOperation {

    final SelectObjectOperation selectObjectOperation;

    final MatchmakerRequestModelMapper matchmakerRequestModelMapper;

    @Override
    public Uni<MatchmakerRequestModel> execute(final SqlConnection sqlConnection,
                                               final int shard,
                                               final Long matchmakerId,
                                               final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, matchmaker_id, created, modified, user_id, client_id, mode, config, 
                            deleted
                        from $schema.tab_matchmaker_request
                        where matchmaker_id = $1 and id = $2
                        limit 1
                        """,
                List.of(
                        matchmakerId,
                        id
                ),
                "Matchmaker request",
                matchmakerRequestModelMapper::execute);
    }
}
