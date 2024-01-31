package com.omgservers.service.module.client.impl.operation.selectActiveClientRuntimesByClientId;

import com.omgservers.model.clientRuntime.ClientRuntimeModel;
import com.omgservers.service.module.client.impl.mapper.ClientRuntimeModelMapper;
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
class SelectActiveClientRuntimesByClientIdOperationImpl implements SelectActiveClientRuntimesByClientIdOperation {

    final SelectListOperation selectListOperation;

    final ClientRuntimeModelMapper clientRuntimeModelMapper;

    @Override
    public Uni<List<ClientRuntimeModel>> selectActiveClientRuntimesByClientId(final SqlConnection sqlConnection,
                                                                              final int shard,
                                                                              final Long clientId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select
                            id, client_id, created, modified, runtime_id, deleted
                        from $schema.tab_client_runtime
                        where client_id = $1 and deleted = false
                        order by id asc
                        """,
                Collections.singletonList(clientId),
                "Client runtime",
                clientRuntimeModelMapper::fromRow);
    }
}
