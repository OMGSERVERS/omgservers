package com.omgservers.service.shard.runtime.impl.operation.runtimeAssignment;

import com.omgservers.service.shard.runtime.impl.mapper.RuntimeAssignmentModelMapper;
import com.omgservers.service.operation.server.ReturnCountOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CountRuntimeAssignmentsOperationImpl implements
        CountRuntimeAssignmentsOperation {

    final ReturnCountOperation returnCountOperation;

    final RuntimeAssignmentModelMapper runtimeAssignmentModelMapper;

    @Override
    public Uni<Integer> execute(final SqlConnection sqlConnection,
                                final int shard,
                                final Long runtimeId) {
        return returnCountOperation.returnCount(
                sqlConnection,
                shard,
                """
                        select id
                        from $shard.tab_runtime_assignment
                        where runtime_id = $1
                        """,
                Collections.singletonList(runtimeId)
        );
    }
}
