package com.omgservers.service.shard.client.impl.operation.clientRuntimeRef;

import com.omgservers.schema.model.clientRuntimeRef.ClientRuntimeRefModel;
import com.omgservers.service.shard.client.impl.mapper.ClientRuntimeRefModelMapper;
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
class SelectClientRuntimeRefOperationImpl implements SelectClientRuntimeRefOperation {

    final SelectObjectOperation selectObjectOperation;

    final ClientRuntimeRefModelMapper clientRuntimeRefModelMapper;

    @Override
    public Uni<ClientRuntimeRefModel> selectClientRuntimeRef(final SqlConnection sqlConnection,
                                                             final int shard,
                                                             Long clientId, final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, client_id, created, modified, runtime_id, deleted
                        from $shard.tab_client_runtime_ref
                        where client_id = $1 and id = $2
                        limit 1
                        """,
                List.of(clientId, id),
                "Client runtime ref",
                clientRuntimeRefModelMapper::fromRow);
    }
}
