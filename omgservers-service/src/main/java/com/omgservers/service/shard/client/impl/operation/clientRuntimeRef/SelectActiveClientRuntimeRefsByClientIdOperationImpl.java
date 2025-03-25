package com.omgservers.service.shard.client.impl.operation.clientRuntimeRef;

import com.omgservers.schema.model.clientRuntimeRef.ClientRuntimeRefModel;
import com.omgservers.service.shard.client.impl.mapper.ClientRuntimeRefModelMapper;
import com.omgservers.service.operation.server.SelectListOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectActiveClientRuntimeRefsByClientIdOperationImpl implements SelectActiveClientRuntimeRefsByClientIdOperation {

    final SelectListOperation selectListOperation;

    final ClientRuntimeRefModelMapper clientRuntimeRefModelMapper;

    @Override
    public Uni<List<ClientRuntimeRefModel>> selectActiveClientRuntimeRefsByClientId(final SqlConnection sqlConnection,
                                                                                    final int shard,
                                                                                    final Long clientId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, client_id, created, modified, runtime_id, deleted
                        from $schema.tab_client_runtime_ref
                        where client_id = $1 and deleted = false
                        order by id asc
                        """,
                List.of(clientId),
                "Client runtime ref",
                clientRuntimeRefModelMapper::fromRow);
    }
}
