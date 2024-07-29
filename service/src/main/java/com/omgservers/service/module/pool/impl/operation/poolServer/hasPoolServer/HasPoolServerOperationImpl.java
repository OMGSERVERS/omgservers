package com.omgservers.service.module.pool.impl.operation.poolServer.hasPoolServer;

import com.omgservers.service.server.operation.hasObject.HasObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HasPoolServerOperationImpl implements HasPoolServerOperation {

    final HasObjectOperation hasObjectOperation;

    @Override
    public Uni<Boolean> hasPoolServer(final SqlConnection sqlConnection,
                                      final int shard,
                                      final Long poolId,
                                      final Long id) {
        return hasObjectOperation.hasObject(
                sqlConnection,
                shard,
                """
                        select id
                        from $schema.tab_pool_server
                        where pool_id = $1 and id = $2 and deleted = false
                        limit 1
                        """,
                List.of(poolId, id),
                "Pool server");
    }
}
