package com.omgservers.service.module.matchmaker.impl.operation.selectRequest;

import com.omgservers.model.request.RequestModel;
import com.omgservers.service.module.matchmaker.impl.mappers.RequestModelMapper;
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
class SelectRequestOperationImpl implements SelectRequestOperation {

    final SelectObjectOperation selectObjectOperation;

    final RequestModelMapper requestModelMapper;

    @Override
    public Uni<RequestModel> selectRequest(final SqlConnection sqlConnection,
                                           final int shard,
                                           final Long matchmakerId,
                                           final Long id,
                                           final Boolean deleted) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, matchmaker_id, created, modified, user_id, client_id, mode, config, deleted
                        from $schema.tab_matchmaker_request
                        where matchmaker_id = $1 and id = $2 and deleted = $3
                        limit 1
                        """,
                Arrays.asList(
                        matchmakerId,
                        id,
                        deleted
                ),
                "Request",
                requestModelMapper::fromRow);
    }
}
