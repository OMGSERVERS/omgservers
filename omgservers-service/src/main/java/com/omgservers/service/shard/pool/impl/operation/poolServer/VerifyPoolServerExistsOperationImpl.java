package com.omgservers.service.shard.pool.impl.operation.poolServer;

import com.omgservers.service.operation.server.VerifyObjectExistsOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class VerifyPoolServerExistsOperationImpl implements VerifyPoolServerExistsOperation {

    final VerifyObjectExistsOperation verifyObjectExistsOperation;

    @Override
    public Uni<Boolean> execute(final SqlConnection sqlConnection,
                                final int slot,
                                final Long poolId,
                                final Long id) {
        return verifyObjectExistsOperation.execute(
                sqlConnection,
                slot,
                """
                        select id
                        from $slot.tab_pool_server
                        where pool_id = $1 and id = $2 and deleted = false
                        limit 1
                        """,
                List.of(poolId, id),
                "Pool server");
    }
}
