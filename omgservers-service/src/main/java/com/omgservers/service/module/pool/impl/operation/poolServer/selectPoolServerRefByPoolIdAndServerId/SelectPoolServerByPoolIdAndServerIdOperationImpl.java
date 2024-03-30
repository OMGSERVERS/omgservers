package com.omgservers.service.module.pool.impl.operation.poolServer.selectPoolServerRefByPoolIdAndServerId;

import com.omgservers.model.poolServerRef.PoolServerRefModel;
import com.omgservers.service.module.pool.impl.mappers.PoolServerRefModelMapper;
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
class SelectPoolServerByPoolIdAndServerIdOperationImpl implements SelectPoolServerByPoolIdAndServerIdOperation {

    final SelectObjectOperation selectObjectOperation;

    final PoolServerRefModelMapper poolServerRefModelMapper;

    @Override
    public Uni<PoolServerRefModel> selectPoolServerRefByPoolIdAndServerId(final SqlConnection sqlConnection,
                                                                          final int shard,
                                                                          final Long poolId,
                                                                          final Long serverId) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, pool_id, created, modified, server_id, deleted
                        from $schema.tab_pool_server_ref
                        where pool_id = $1 and server_id = $2
                        order by id desc
                        limit 1
                        """,
                List.of(poolId, serverId),
                "Pool server ref",
                poolServerRefModelMapper::fromRow);
    }
}
