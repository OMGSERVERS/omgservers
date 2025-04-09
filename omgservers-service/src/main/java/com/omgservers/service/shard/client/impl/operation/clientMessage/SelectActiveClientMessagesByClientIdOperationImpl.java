package com.omgservers.service.shard.client.impl.operation.clientMessage;

import com.omgservers.schema.model.clientMessage.ClientMessageModel;
import com.omgservers.service.shard.client.impl.mapper.ClientMessageModelMapper;
import com.omgservers.service.operation.server.SelectListOperation;
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
class SelectActiveClientMessagesByClientIdOperationImpl
        implements SelectActiveClientMessagesByClientIdOperation {

    final SelectListOperation selectListOperation;

    final ClientMessageModelMapper clientMessageModelMapper;

    @Override
    public Uni<List<ClientMessageModel>> selectActiveClientMessagesByClientId(
            final SqlConnection sqlConnection,
            final int shard,
            final Long clientId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, client_id, created, modified, qualifier, body, deleted
                        from $shard.tab_client_message
                        where client_id = $1 and deleted = false
                        order by id asc
                        """,
                Collections.singletonList(clientId),
                "Client message",
                clientMessageModelMapper::fromRow);
    }
}
