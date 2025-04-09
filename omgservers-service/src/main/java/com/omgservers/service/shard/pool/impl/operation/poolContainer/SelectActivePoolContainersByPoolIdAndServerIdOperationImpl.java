package com.omgservers.service.shard.pool.impl.operation.poolContainer;

import com.omgservers.schema.model.poolSeverContainer.PoolContainerModel;
import com.omgservers.service.operation.server.SelectListOperation;
import com.omgservers.service.shard.pool.impl.mappers.PoolContainerModelMapper;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectActivePoolContainersByPoolIdAndServerIdOperationImpl
        implements SelectActivePoolContainersByPoolIdAndServerIdOperation {

    final SelectListOperation selectListOperation;

    final PoolContainerModelMapper poolContainerModelMapper;

    @Override
    public Uni<List<PoolContainerModel>> execute(final SqlConnection sqlConnection,
                                                 final int shard,
                                                 final Long poolId,
                                                 final Long serverId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, pool_id, server_id, created, modified, runtime_id, runtime_qualifier,
                            config, deleted
                        from $shard.tab_pool_container
                        where pool_id = $1 and server_id = $2 and deleted = false
                        order by id asc
                        """,
                List.of(poolId, serverId),
                "Pool server container",
                poolContainerModelMapper::execute);
    }
}
