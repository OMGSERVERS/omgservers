package com.omgservers.service.module.client.impl.operation.selectClientRuntime;

import com.omgservers.model.clientRuntime.ClientRuntimeModel;
import com.omgservers.service.module.client.impl.mapper.ClientRuntimeModelMapper;
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
class SelectClientRuntimeOperationImpl implements SelectClientRuntimeOperation {

    final SelectObjectOperation selectObjectOperation;

    final ClientRuntimeModelMapper clientRuntimeModelMapper;

    @Override
    public Uni<ClientRuntimeModel> selectClientRuntime(final SqlConnection sqlConnection,
                                                       final int shard,
                                                       Long clientId, final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select 
                            id, client_id, created, modified, runtime_id, deleted
                        from $schema.tab_client_runtime
                        where client_id = $1 and id = $2
                        limit 1
                        """,
                Arrays.asList(clientId, id),
                "Client runtime",
                clientRuntimeModelMapper::fromRow);
    }
}
