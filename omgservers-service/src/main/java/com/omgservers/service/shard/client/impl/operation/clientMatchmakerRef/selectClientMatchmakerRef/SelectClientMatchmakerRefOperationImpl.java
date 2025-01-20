package com.omgservers.service.shard.client.impl.operation.clientMatchmakerRef.selectClientMatchmakerRef;

import com.omgservers.schema.model.clientMatchmakerRef.ClientMatchmakerRefModel;
import com.omgservers.service.shard.client.impl.mapper.ClientMatchmakerRefModelMapper;
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
class SelectClientMatchmakerRefOperationImpl implements SelectClientMatchmakerRefOperation {

    final SelectObjectOperation selectObjectOperation;

    final ClientMatchmakerRefModelMapper clientMatchmakerRefModelMapper;

    @Override
    public Uni<ClientMatchmakerRefModel> selectClientMatchmakerRef(final SqlConnection sqlConnection,
                                                                   final int shard,
                                                                   Long clientId, final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, client_id, created, modified, matchmaker_id, deleted
                        from $schema.tab_client_matchmaker_ref
                        where client_id = $1 and id = $2
                        limit 1
                        """,
                List.of(clientId, id),
                "Client matchmaker ref",
                clientMatchmakerRefModelMapper::fromRow);
    }
}
