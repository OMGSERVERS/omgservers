package com.omgservers.service.shard.pool.impl.operation.poolServer;

import com.omgservers.service.operation.server.HasObjectOperation;
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

    final HasObjectOperation hasObjectOperation;

    @Override
    public Uni<Boolean> execute(final SqlConnection sqlConnection,
                                final int shard,
                                final Long poolId,
                                final Long id) {
        return hasObjectOperation.hasObject(
                sqlConnection,
                shard,
                """
                        select id
                        from $shard.tab_pool_server
                        where pool_id = $1 and id = $2 and deleted = false
                        limit 1
                        """,
                List.of(poolId, id),
                "Pool server");
    }
}
