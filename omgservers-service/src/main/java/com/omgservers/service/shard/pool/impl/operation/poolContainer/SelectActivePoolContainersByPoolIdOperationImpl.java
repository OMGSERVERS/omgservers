package com.omgservers.service.shard.pool.impl.operation.poolContainer;

import com.omgservers.schema.model.poolContainer.PoolContainerModel;
import com.omgservers.service.shard.pool.impl.mappers.PoolContainerModelMapper;
import com.omgservers.service.operation.server.SelectListOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectActivePoolContainersByPoolIdOperationImpl
        implements SelectActivePoolContainersByPoolIdOperation {

    final SelectListOperation selectListOperation;

    final PoolContainerModelMapper poolContainerModelMapper;

    @Override
    public Uni<List<PoolContainerModel>> execute(final SqlConnection sqlConnection,
                                                 final int slot,
                                                 final Long poolId) {
        return selectListOperation.selectList(
                sqlConnection,
                slot,
                """
                        select
                            id, idempotency_key, pool_id, server_id, created, modified, runtime_id, runtime_qualifier,
                            config, deleted
                        from $slot.tab_pool_container
                        where pool_id = $1 and deleted = false
                        order by id asc
                        """,
                List.of(poolId),
                "Pool server container",
                poolContainerModelMapper::execute);
    }
}
