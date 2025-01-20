package com.omgservers.service.shard.pool.impl.operation.poolRequest;

import com.omgservers.service.event.body.module.pool.PoolRequestDeletedEventBodyModel;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeletePoolRequestOperationImpl implements DeletePoolRequestOperation {

    final ChangeObjectOperation changeObjectOperation;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int shard,
                                final Long poolId,
                                final Long id) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        update $schema.tab_pool_request
                        set modified = $3, deleted = true
                        where pool_id = $1 and id = $2 and deleted = false
                        """,
                List.of(
                        poolId,
                        id,
                        Instant.now().atOffset(ZoneOffset.UTC)
                ),
                () -> new PoolRequestDeletedEventBodyModel(poolId, id),
                () -> null
        );
    }
}
