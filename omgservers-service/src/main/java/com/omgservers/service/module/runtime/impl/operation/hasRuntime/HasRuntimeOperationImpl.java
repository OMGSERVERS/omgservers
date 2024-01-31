package com.omgservers.service.module.runtime.impl.operation.hasRuntime;

import com.omgservers.service.operation.hasObject.HasObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HasRuntimeOperationImpl implements HasRuntimeOperation {

    final HasObjectOperation hasObjectOperation;

    @Override
    public Uni<Boolean> hasRuntime(final SqlConnection sqlConnection,
                                   final int shard,
                                   final Long id) {
        return hasObjectOperation.hasObject(
                sqlConnection,
                shard,
                """
                        select id
                        from $schema.tab_runtime
                        where id = $1 and deleted = false
                        limit 1
                        """,
                Collections.singletonList(id),
                "Runtime");
    }
}
