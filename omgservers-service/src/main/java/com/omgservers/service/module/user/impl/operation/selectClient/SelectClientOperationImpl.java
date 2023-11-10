package com.omgservers.service.module.user.impl.operation.selectClient;

import com.omgservers.model.client.ClientModel;
import com.omgservers.service.module.user.impl.mapper.ClientModelMapper;
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
class SelectClientOperationImpl implements SelectClientOperation {

    final SelectObjectOperation selectObjectOperation;

    final ClientModelMapper clientModelMapper;

    @Override
    public Uni<ClientModel> selectClient(final SqlConnection sqlConnection,
                                         final int shard,
                                         final Long userId,
                                         final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select 
                            id, user_id, player_id, created, modified, server, connection_id, 
                            version_id, default_matchmaker_id, default_runtime_id, deleted
                        from $schema.tab_user_client
                        where user_id = $1 and id = $2
                        limit 1
                        """,
                Arrays.asList(
                        userId,
                        id
                ),
                "Client",
                clientModelMapper::fromRow);
    }
}
