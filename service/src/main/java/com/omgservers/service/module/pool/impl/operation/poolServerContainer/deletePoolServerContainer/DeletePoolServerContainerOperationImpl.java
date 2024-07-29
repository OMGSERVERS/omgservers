package com.omgservers.service.module.pool.impl.operation.poolServerContainer.deletePoolServerContainer;

import com.omgservers.schema.event.body.module.pool.PoolServerContainerDeletedEventBodyModel;
import com.omgservers.service.server.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
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
class DeletePoolServerContainerOperationImpl implements DeletePoolServerContainerOperation {

    final ChangeObjectOperation changeObjectOperation;

    @Override
    public Uni<Boolean> deletePoolServerContainer(final ChangeContext<?> changeContext,
                                                  final SqlConnection sqlConnection,
                                                  final int shard,
                                                  final Long poolId,
                                                  final Long serverId,
                                                  final Long id) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        update $schema.tab_pool_server_container
                        set modified = $4, deleted = true
                        where pool_id = $1 and server_id = $2 and id = $3 and deleted = false
                        """,
                List.of(
                        poolId,
                        serverId,
                        id,
                        Instant.now().atOffset(ZoneOffset.UTC)
                ),
                () -> new PoolServerContainerDeletedEventBodyModel(poolId, serverId, id),
                () -> null
        );
    }
}
