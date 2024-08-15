package com.omgservers.service.module.client.impl.operation.clientMessage.selectActiveClientMessagesByClientId;

import com.omgservers.schema.model.clientMessage.ClientMessageModel;
import com.omgservers.service.module.client.impl.mapper.ClientMessageModelMapper;
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
                        from $schema.tab_client_message
                        where client_id = $1 and deleted = false
                        order by id asc
                        """,
                Collections.singletonList(clientId),
                "Client message",
                clientMessageModelMapper::fromRow);
    }
}
