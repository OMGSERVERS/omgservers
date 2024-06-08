package com.omgservers.service.module.root.impl.operation.root.hasRoot;

import com.omgservers.service.operation.hasObject.HasObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HasRootOperationImpl implements HasRootOperation {

    final HasObjectOperation hasObjectOperation;

    @Override
    public Uni<Boolean> hasRoot(final SqlConnection sqlConnection,
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
