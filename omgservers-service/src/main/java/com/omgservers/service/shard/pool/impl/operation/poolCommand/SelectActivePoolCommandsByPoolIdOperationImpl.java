package com.omgservers.service.shard.pool.impl.operation.poolCommand;

import com.omgservers.schema.model.poolCommand.PoolCommandModel;
import com.omgservers.service.operation.server.SelectListOperation;
import com.omgservers.service.shard.pool.impl.mappers.PoolCommandModelMapper;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectActivePoolCommandsByPoolIdOperationImpl
        implements SelectActivePoolCommandsByPoolIdOperation {

    final SelectListOperation selectListOperation;

    final PoolCommandModelMapper poolCommandModelMapper;

    @Override
    public Uni<List<PoolCommandModel>> execute(final SqlConnection sqlConnection,
                                               final int shard,
                                               final Long poolId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, pool_id, created, modified, qualifier, body, deleted
                        from $schema.tab_pool_command
                        where pool_id = $1 and deleted = false
                        order by id asc
                        """,
                List.of(poolId),
                "Pool command",
                poolCommandModelMapper::execute);
    }
}
