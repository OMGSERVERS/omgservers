package com.omgservers.service.module.pool.impl.operation.selectPool;

import com.omgservers.model.pool.PoolModel;
import com.omgservers.service.module.pool.impl.mappers.PoolModelMapper;
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
class SelectPoolOperationImpl implements SelectPoolOperation {

    final SelectObjectOperation selectObjectOperation;

    final PoolModelMapper poolModelMapper;

    @Override
    public Uni<PoolModel> selectPool(final SqlConnection sqlConnection,
                                     final int shard,
                                     final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, created, modified, root_id, deleted
                        from $schema.tab_pool
                        where id = $1
                        limit 1
                        """,
                List.of(id),
                "Pool",
                poolModelMapper::fromRow);
    }
}
