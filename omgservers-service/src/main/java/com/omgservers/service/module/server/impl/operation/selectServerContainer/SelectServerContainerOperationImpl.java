package com.omgservers.service.module.server.impl.operation.selectServerContainer;

import com.omgservers.model.serverContainer.ServerContainerModel;
import com.omgservers.service.module.server.impl.mapper.ServerContainerModelMapper;
import com.omgservers.service.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectServerContainerOperationImpl implements SelectServerContainerOperation {

    final SelectObjectOperation selectObjectOperation;

    final ServerContainerModelMapper serverContainerModelMapper;

    @Override
    public Uni<ServerContainerModel> selectServerContainer(final SqlConnection sqlConnection,
                                                           final int shard,
                                                           final Long serverId,
                                                           final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, server_id, created, modified, image, cpu_limit, memory_limit, config,
                            deleted
                        from $schema.tab_server_container
                        where server_id = $1 and id = $2
                        limit 1
                        """,
                List.of(serverId, id),
                "Server container",
                serverContainerModelMapper::fromRow);
    }
}
