package com.omgservers.service.module.pool.impl.operation.poolServer.deletePoolServer;

import com.omgservers.model.event.body.module.pool.PoolServerDeletedEventBodyModel;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
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
class DeletePoolServerOperationImpl implements DeletePoolServerOperation {

    final ChangeObjectOperation changeObjectOperation;

    @Override
    public Uni<Boolean> deletePoolServer(final ChangeContext<?> changeContext,
                                         final SqlConnection sqlConnection,
                                         final int shard,
                                         final Long poolId,
                                         final Long id) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        update $schema.tab_pool_server
                        set modified = $3, deleted = true
                        where pool_id = $1 and id = $2 and deleted = false
                        """,
                List.of(
                        poolId,
                        id,
                        Instant.now().atOffset(ZoneOffset.UTC)
                ),
                () -> new PoolServerDeletedEventBodyModel(poolId, id),
                () -> null
        );
    }
}
