package com.omgservers.service.shard.pool.impl.operation.poolContainer;

import com.omgservers.service.event.body.module.pool.PoolContainerDeletedEventBodyModel;
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
class DeletePoolContainerOperationImpl implements DeletePoolContainerOperation {

    final ChangeObjectOperation changeObjectOperation;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int shard,
                                final Long poolId,
                                final Long serverId,
                                final Long id) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        update $schema.tab_pool_container
                        set modified = $4, deleted = true
                        where pool_id = $1 and server_id = $2 and id = $3 and deleted = false
                        """,
                List.of(
                        poolId,
                        serverId,
                        id,
                        Instant.now().atOffset(ZoneOffset.UTC)
                ),
                () -> new PoolContainerDeletedEventBodyModel(poolId, serverId, id),
                () -> null
        );
    }
}
