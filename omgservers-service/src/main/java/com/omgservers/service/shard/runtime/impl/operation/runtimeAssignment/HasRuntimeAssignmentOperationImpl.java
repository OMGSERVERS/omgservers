package com.omgservers.service.shard.runtime.impl.operation.runtimeAssignment;

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
class HasRuntimeAssignmentOperationImpl implements HasRuntimeAssignmentOperation {

    final VerifyObjectExistsOperation verifyObjectExistsOperation;

    @Override
    public Uni<Boolean> execute(final SqlConnection sqlConnection,
                                final int slot,
                                final Long runtimeId,
                                final Long clientId) {
        return verifyObjectExistsOperation.execute(
                sqlConnection,
                slot,
                """
                        select id
                        from $slot.tab_runtime_assignment
                        where
                            runtime_id = $1 and client_id = $2 and deleted = false
                        limit 1
                        """,
                List.of(runtimeId, clientId),
                "Runtime client");
    }
}
