package com.omgservers.service.shard.pool.impl.operation.poolRequest;

import com.omgservers.schema.model.poolRequest.PoolRequestModel;
import com.omgservers.service.operation.server.SelectObjectOperation;
import com.omgservers.service.shard.pool.impl.mappers.PoolRequestModelMapper;
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
    public Uni<PoolRequestModel> execute(final SqlConnection sqlConnection,
                                         final int slot,
                                         final Long poolId,
                                         final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                slot,
                """
                        select
                            id, idempotency_key, pool_id, created, modified, runtime_id, runtime_qualifier, config,
                            deleted
                        from $slot.tab_pool_request
                        where pool_id = $1 and id = $2
                        limit 1
                        """,
                List.of(poolId, id),
                "Pool request",
                poolRequestModelMapper::execute);
    }
}
