package com.omgservers.service.module.server.impl.operation.serverContainer.selectActiveServerContainersByServerId;

import com.omgservers.model.serverContainer.ServerContainerModel;
import com.omgservers.service.module.server.impl.mapper.ServerContainerModelMapper;
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
class SelectActiveServerContainersByServerIdOperationImpl
        implements SelectActiveServerContainersByServerIdOperation {

    final SelectListOperation selectListOperation;

    final ServerContainerModelMapper serverContainerModelMapper;

    @Override
    public Uni<List<ServerContainerModel>> selectActiveServerContainersByServerId(
            final SqlConnection sqlConnection,
            final int shard,
            final Long id) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, server_id, created, modified, runtime_id, image, cpu_limit,
                            memory_limit, config, deleted
                        from $schema.tab_server_container
                        where server_id = $1 and deleted = false
                        order by id asc
                        """,
                Collections.singletonList(id),
                "Server container",
                serverContainerModelMapper::fromRow);
    }
}
