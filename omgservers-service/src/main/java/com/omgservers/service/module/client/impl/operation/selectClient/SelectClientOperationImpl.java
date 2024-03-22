package com.omgservers.service.module.client.impl.operation.selectClient;

import com.omgservers.model.client.ClientModel;
import com.omgservers.service.module.client.impl.mapper.ClientModelMapper;
import com.omgservers.service.operation.selectObject.SelectObjectOperation;
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
                            id, idempotency_key, created, modified, user_id, player_id, tenant_id, version_id, deleted
                        from $schema.tab_client
                        where id = $1
                        limit 1
                        """,
                Collections.singletonList(id),
                "Client",
                clientModelMapper::fromRow);
    }
}
