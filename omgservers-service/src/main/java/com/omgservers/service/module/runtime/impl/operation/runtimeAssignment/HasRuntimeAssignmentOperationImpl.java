package com.omgservers.service.module.runtime.impl.operation.runtimeAssignment;

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
class HasRuntimeAssignmentOperationImpl implements HasRuntimeAssignmentOperation {

    final HasObjectOperation hasObjectOperation;

    @Override
    public Uni<Boolean> execute(final SqlConnection sqlConnection,
                                final int shard,
                                final Long runtimeId,
                                final Long clientId) {
        return hasObjectOperation.hasObject(
                sqlConnection,
                shard,
                """
                        select id
                        from $schema.tab_runtime_assignment
                        where
                            runtime_id = $1 and client_id = $2 and deleted = false
                        limit 1
                        """,
                List.of(runtimeId, clientId),
                "Runtime client");
    }
}
