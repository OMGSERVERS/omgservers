package com.omgservers.service.shard.client.impl.operation.client;

import com.omgservers.schema.model.client.ClientModel;
import com.omgservers.service.operation.server.SelectObjectOperation;
import com.omgservers.service.shard.client.impl.mapper.ClientModelMapper;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectClientOperationImpl implements SelectClientOperation {

    final SelectObjectOperation selectObjectOperation;

    final ClientModelMapper clientModelMapper;

    @Override
    public Uni<ClientModel> selectClient(final SqlConnection sqlConnection,
                                         final int shard,
                                         final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, created, modified, user_id, player_id, deployment_id, config, deleted
                        from $shard.tab_client
                        where id = $1
                        limit 1
                        """,
                Collections.singletonList(id),
                "Client",
                clientModelMapper::fromRow);
    }
}
