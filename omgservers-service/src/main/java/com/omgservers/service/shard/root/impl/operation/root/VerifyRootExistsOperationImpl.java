package com.omgservers.service.shard.root.impl.operation.root;

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
class VerifyRootExistsOperationImpl implements VerifyRootExistsOperation {

    final HasObjectOperation hasObjectOperation;

    @Override
    public Uni<Boolean> execute(final SqlConnection sqlConnection,
                                final int shard,
                                final Long id) {
        return hasObjectOperation.hasObject(
                sqlConnection,
                shard,
                """
                        select id
                        from $schema.tab_root
                        where id = $1 and deleted = false
                        limit 1
                        """,
                List.of(id),
                "Root");
    }
}
