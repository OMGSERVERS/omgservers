package com.omgservers.service.module.pool.impl.operation.poolServerRef.selectActivePoolServerRefsByPoolId;

import com.omgservers.model.poolServerRef.PoolServerRefModel;
import com.omgservers.service.module.pool.impl.mappers.PoolServerRefModelMapper;
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
class SelectActivePoolServerRefsByPoolIdOperationImpl
        implements SelectActivePoolServerRefsByPoolIdOperation {

    final SelectListOperation selectListOperation;

    final PoolServerRefModelMapper poolServerRefModelMapper;

    @Override
    public Uni<List<PoolServerRefModel>> selectActivePoolServerRefsByPoolId(
            final SqlConnection sqlConnection,
            final int shard,
            final Long poolId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, pool_id, created, modified, server_id, deleted
                        from $schema.tab_pool_server_ref
                        where pool_id = $1 and deleted = false
                        order by id asc
                        """,
                List.of(poolId),
                "Pool server ref",
                poolServerRefModelMapper::fromRow);
    }
}
