package com.omgservers.service.shard.pool.impl.operation.poolCommand;

import com.omgservers.schema.model.poolCommand.PoolCommandModel;
import com.omgservers.service.operation.server.SelectObjectOperation;
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
class SelectPoolCommandOperationImpl implements SelectPoolCommandOperation {

    final SelectObjectOperation selectObjectOperation;

    final PoolCommandModelMapper poolCommandModelMapper;

    @Override
    public Uni<PoolCommandModel> execute(
            final SqlConnection sqlConnection,
            final int shard,
            final Long poolId,
            final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, pool_id, created, modified, qualifier, body, deleted
                        from $schema.tab_pool_command
                        where pool_id = $1 and id = $2
                        limit 1
                        """,
                List.of(poolId, id),
                "Pool command",
                poolCommandModelMapper::execute);
    }
}
