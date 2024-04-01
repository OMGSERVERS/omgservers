package com.omgservers.service.module.pool.impl.operation.poolRuntimeServerContainerRequest.selectActivePoolRuntimeServerContainerRequestsByPoolId;

import com.omgservers.model.poolRuntimeServerContainerRequest.PoolRuntimeServerContainerRequestModel;
import com.omgservers.service.module.pool.impl.mappers.PoolRuntimeServerContainerRequestModelMapper;
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
class SelectActivePoolRuntimeServerContainerRequestsByPoolIdOperationImpl
        implements SelectActivePoolRuntimeServerContainerRequestsByPoolIdOperation {

    final SelectListOperation selectListOperation;

    final PoolRuntimeServerContainerRequestModelMapper poolRuntimeServerContainerRequestModelMapper;

    @Override
    public Uni<List<PoolRuntimeServerContainerRequestModel>> selectActivePoolRuntimeServerContainerRequestsByPoolId(
            final SqlConnection sqlConnection,
            final int shard,
            final Long poolId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, pool_id, created, modified, runtime_id, config, deleted
                        from $schema.tab_pool_runtime_server_container_request
                        where pool_id = $1 and deleted = false
                        order by id asc
                        """,
                List.of(poolId),
                "Pool runtime server container request",
                poolRuntimeServerContainerRequestModelMapper::fromRow);
    }
}
