package com.omgservers.service.module.pool.impl.operation.poolRuntimeServerContainerRef.selectActivePoolRuntimeServerContainerRefsByPoolId;

import com.omgservers.model.poolRuntimeSeverContainerRef.PoolRuntimeServerContainerRefModel;
import com.omgservers.service.module.pool.impl.mappers.PoolRuntimeServerContainerRefModelMapper;
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
class SelectActivePoolRuntimeServerContainerRefsByPoolIdOperationImpl
        implements SelectActivePoolRuntimeServerContainerRefsByPoolIdOperation {

    final SelectListOperation selectListOperation;

    final PoolRuntimeServerContainerRefModelMapper poolRuntimeServerContainerRefModelMapper;

    @Override
    public Uni<List<PoolRuntimeServerContainerRefModel>> selectActivePoolRuntimeServerContainerRefsByPoolId(
            final SqlConnection sqlConnection,
            final int shard,
            final Long poolId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, pool_id, created, modified, runtime_id, server_id, container_id, 
                            deleted
                        from $schema.tab_pool_runtime_server_container_ref
                        where pool_id = $1 and deleted = false
                        order by id asc
                        """,
                List.of(poolId),
                "Pool runtime server container ref",
                poolRuntimeServerContainerRefModelMapper::fromRow);
    }
}
