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
class SelectClientRuntimeRefByClientIdAndRuntimeIdOperationImpl
        implements SelectClientRuntimeRefByClientIdAndRuntimeIdOperation {

    final SelectObjectOperation selectObjectOperation;

    final ClientRuntimeRefModelMapper clientRuntimeRefModelMapper;

    @Override
    public Uni<ClientRuntimeRefModel> selectClientRuntimeRefByClientIdAndRuntimeId(final SqlConnection sqlConnection,
                                                                                   final int slot,
                                                                                   final Long clientId,
                                                                                   final Long runtimeId) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                slot,
                """
                        select id, idempotency_key, client_id, created, modified, runtime_id, deleted
                        from $slot.tab_client_runtime_ref
                        where client_id = $1 and runtime_id = $2
                        order by id desc
                        limit 1
                        """,
                List.of(clientId, runtimeId),
                "Client runtime ref",
                clientRuntimeRefModelMapper::fromRow);
    }
}
