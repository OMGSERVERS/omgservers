package com.omgservers.service.module.pool.impl.operation.poolServer;

import com.omgservers.schema.model.poolServer.PoolServerModel;
import com.omgservers.service.module.pool.impl.mappers.PoolServerModelMapper;
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
class SelectPoolServerOperationImpl implements SelectPoolServerOperation {

    final SelectObjectOperation selectObjectOperation;

    final PoolServerModelMapper poolServerModelMapper;

    @Override
    public Uni<PoolServerModel> execute(final SqlConnection sqlConnection,
                                        final int shard,
                                        final Long poolId,
                                        final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, pool_id, created, modified, qualifier, config, deleted
                        from $schema.tab_pool_server
                        where id = $1
                        limit 1
                        """,
                List.of(id),
                "Pool server",
                poolServerModelMapper::execute);
    }
}
