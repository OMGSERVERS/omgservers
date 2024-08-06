package com.omgservers.service.module.pool.impl.operation.poolRequest.selectPoolRequest;

import com.omgservers.schema.model.poolRequest.PoolRequestModel;
import com.omgservers.service.module.pool.impl.mappers.PoolRequestModelMapper;
import com.omgservers.service.server.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectPoolRequestOperationImpl implements SelectPoolRequestOperation {

    final SelectObjectOperation selectObjectOperation;

    final PoolRequestModelMapper poolRequestModelMapper;

    @Override
    public Uni<PoolRequestModel> selectPoolRequest(
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
                        from $schema.tab_pool_request
                        where pool_id = $1 and id = $2
                        limit 1
                        """,
                List.of(poolId, id),
                "Pool request",
                poolRequestModelMapper::fromRow);
    }
}
