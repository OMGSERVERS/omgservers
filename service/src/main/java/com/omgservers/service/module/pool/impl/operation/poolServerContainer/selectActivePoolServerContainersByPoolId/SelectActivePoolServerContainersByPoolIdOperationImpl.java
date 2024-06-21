package com.omgservers.service.module.pool.impl.operation.poolServerContainer.selectActivePoolServerContainersByPoolId;

import com.omgservers.model.poolSeverContainer.PoolServerContainerModel;
import com.omgservers.service.module.pool.impl.mappers.PoolServerContainerModelMapper;
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
class SelectActivePoolServerContainersByPoolIdOperationImpl
        implements SelectActivePoolServerContainersByPoolIdOperation {

    final SelectListOperation selectListOperation;

    final PoolServerContainerModelMapper poolServerContainerModelMapper;

    @Override
    public Uni<List<PoolServerContainerModel>> selectActivePoolServerContainersByPoolId(
            final SqlConnection sqlConnection,
            final int shard,
            final Long poolId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, pool_id, server_id, created, modified, runtime_id, config, deleted
                        from $schema.tab_pool_server_container
                        where pool_id = $1 and deleted = false
                        order by id asc
                        """,
                List.of(poolId),
                "Pool server container",
                poolServerContainerModelMapper::fromRow);
    }
}
