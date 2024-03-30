package com.omgservers.service.module.client.impl.operation.clientMatchmakerRef.selectActiveClientMatchmakerRefsByClientId;

import com.omgservers.model.clientMatchmakerRef.ClientMatchmakerRefModel;
import com.omgservers.service.module.client.impl.mapper.ClientMatchmakerRefModelMapper;
import com.omgservers.service.operation.selectList.SelectListOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectActiveClientMatchmakerRefsByClientIdOperationImpl
        implements SelectActiveClientMatchmakerRefsByClientIdOperation {

    final SelectListOperation selectListOperation;

    final ClientMatchmakerRefModelMapper clientMatchmakerRefModelMapper;

    @Override
    public Uni<List<ClientMatchmakerRefModel>> selectActiveClientMatchmakerRefsByClientId(
            final SqlConnection sqlConnection,
            final int shard,
            final Long clientId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, client_id, created, modified, matchmaker_id, deleted
                        from $schema.tab_client_matchmaker_ref
                        where client_id = $1 and deleted = false
                        order by id asc
                        """,
                List.of(clientId),
                "Client matchmaker ref",
                clientMatchmakerRefModelMapper::fromRow);
    }
}
