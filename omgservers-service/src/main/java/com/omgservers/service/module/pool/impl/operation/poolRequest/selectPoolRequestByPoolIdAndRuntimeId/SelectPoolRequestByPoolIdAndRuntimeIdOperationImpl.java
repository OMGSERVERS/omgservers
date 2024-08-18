package com.omgservers.service.module.pool.impl.operation.poolRequest.selectPoolRequestByPoolIdAndRuntimeId;

import com.omgservers.schema.model.poolRequest.PoolRequestModel;
import com.omgservers.service.module.pool.impl.mappers.PoolRequestModelMapper;
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
class SelectPoolRequestByPoolIdAndRuntimeIdOperationImpl
        implements SelectPoolRequestByPoolIdAndRuntimeIdOperation {

    final SelectObjectOperation selectObjectOperation;

    final PoolRequestModelMapper poolRequestModelMapper;

    @Override
    public Uni<PoolRequestModel> selectPoolRequestByPoolIdAndRuntimeId(
            final SqlConnection sqlConnection,
            final int shard,
            final Long poolId,
            final Long runtimeId) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, pool_id, created, modified, runtime_id, runtime_qualifier, config, deleted
                        from $schema.tab_pool_request
                        where pool_id = $1 and runtime_id = $2
                        order by id desc
                        limit 1
                        """,
                List.of(poolId, runtimeId),
                "Pool request",
                poolRequestModelMapper::fromRow);
    }
}
