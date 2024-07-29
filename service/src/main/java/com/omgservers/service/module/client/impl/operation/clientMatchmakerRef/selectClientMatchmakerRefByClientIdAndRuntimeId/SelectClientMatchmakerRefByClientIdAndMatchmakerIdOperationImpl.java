package com.omgservers.service.module.client.impl.operation.clientMatchmakerRef.selectClientMatchmakerRefByClientIdAndRuntimeId;

import com.omgservers.schema.model.clientMatchmakerRef.ClientMatchmakerRefModel;
import com.omgservers.service.module.client.impl.mapper.ClientMatchmakerRefModelMapper;
import com.omgservers.service.server.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectClientMatchmakerRefByClientIdAndMatchmakerIdOperationImpl
        implements SelectClientMatchmakerRefByClientIdAndMatchmakerIdOperation {

    final SelectObjectOperation selectObjectOperation;

    final ClientMatchmakerRefModelMapper clientMatchmakerRefModelMapper;

    @Override
    public Uni<ClientMatchmakerRefModel> selectClientMatchmakerRefByClientIdAndMatchmakerId(
            final SqlConnection sqlConnection,
            final int shard,
            final Long clientId,
            final Long matchmakerId) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, client_id, created, modified, matchmaker_id, deleted
                        from $schema.tab_client_matchmaker_ref
                        where client_id = $1 and matchmaker_id = $2
                        order by id desc
                        limit 1
                        """,
                List.of(clientId, matchmakerId),
                "Client matchmaker ref",
                clientMatchmakerRefModelMapper::fromRow);
    }
}
