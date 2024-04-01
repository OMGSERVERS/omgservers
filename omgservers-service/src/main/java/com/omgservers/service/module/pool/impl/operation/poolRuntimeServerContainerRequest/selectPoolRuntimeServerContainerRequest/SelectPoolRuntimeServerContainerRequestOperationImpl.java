package com.omgservers.service.module.pool.impl.operation.poolRuntimeServerContainerRequest.selectPoolRuntimeServerContainerRequest;

import com.omgservers.model.poolRuntimeServerContainerRequest.PoolRuntimeServerContainerRequestModel;
import com.omgservers.service.module.pool.impl.mappers.PoolRuntimeServerContainerRequestModelMapper;
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
class SelectPoolRuntimeServerContainerRequestOperationImpl implements SelectPoolRuntimeServerContainerRequestOperation {

    final SelectObjectOperation selectObjectOperation;

    final PoolRuntimeServerContainerRequestModelMapper poolRuntimeServerContainerRequestModelMapper;

    @Override
    public Uni<PoolRuntimeServerContainerRequestModel> selectPoolRuntimeServerContainerRequest(
            final SqlConnection sqlConnection,
            final int shard,
            final Long poolId,
            final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, pool_id, created, modified, runtime_id, config, deleted
                        from $schema.tab_pool_runtime_server_container_request
                        where pool_id = $1 and id = $2
                        limit 1
                        """,
                List.of(poolId, id),
                "Pool runtime server container request",
                poolRuntimeServerContainerRequestModelMapper::fromRow);
    }
}
