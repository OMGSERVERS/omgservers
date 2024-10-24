package com.omgservers.service.module.pool.impl.operation.poolContainer;

import com.omgservers.schema.model.poolSeverContainer.PoolContainerModel;
import com.omgservers.service.module.pool.impl.mappers.PoolContainerModelMapper;
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
class SelectPoolContainerByPoolIdAndRuntimeIdOperationImpl
        implements SelectPoolContainerByPoolIdAndRuntimeIdOperation {

    final SelectObjectOperation selectObjectOperation;

    final PoolContainerModelMapper poolContainerModelMapper;

    @Override
    public Uni<PoolContainerModel> execute(
            final SqlConnection sqlConnection,
            final int shard,
            final Long poolId,
            final Long serverId,
            final Long runtimeId) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, pool_id, server_id, created, modified, runtime_id, runtime_qualifier, config, deleted
                        from $schema.tab_pool_container
                        where pool_id = $1 and server_id = $2 and runtime_id = $3
                        order by id desc
                        limit 1
                        """,
                List.of(poolId, serverId, runtimeId),
                "Pool server container",
                poolContainerModelMapper::execute);
    }
}
