package com.omgservers.service.shard.pool.impl.operation.poolRequest;

import com.omgservers.schema.model.poolRequest.PoolRequestModel;
import com.omgservers.service.shard.pool.impl.mappers.PoolRequestModelMapper;
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
class SelectActivePoolRequestsByPoolIdOperationImpl
        implements SelectActivePoolRequestsByPoolIdOperation {

    final SelectListOperation selectListOperation;

    final PoolRequestModelMapper poolRequestModelMapper;

    @Override
    public Uni<List<PoolRequestModel>> execute(
            final SqlConnection sqlConnection,
            final int shard,
            final Long poolId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, pool_id, created, modified, runtime_id, runtime_qualifier, config, deleted
                        from $schema.tab_pool_request
                        where pool_id = $1 and deleted = false
                        order by id asc
                        """,
                List.of(poolId),
                "Pool request",
                poolRequestModelMapper::execute);
    }
}
