package com.omgservers.service.module.runtime.impl.operation.runtimeAssignment.selectActiveRuntimeAssignmentsByRuntimeId;

import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
import com.omgservers.service.module.runtime.impl.mapper.RuntimeAssignmentModelMapper;
import com.omgservers.service.operation.selectList.SelectListOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectActiveRuntimeAssignmentsByRuntimeIdOperationImpl implements
        SelectActiveRuntimeAssignmentsByRuntimeIdOperation {

    final SelectListOperation selectListOperation;

    final RuntimeAssignmentModelMapper runtimeAssignmentModelMapper;

    @Override
    public Uni<List<RuntimeAssignmentModel>> selectActiveRuntimeAssignmentsByRuntimeId(
            final SqlConnection sqlConnection,
            final int shard,
            final Long runtimeId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, runtime_id, created, modified, client_id, last_activity, config, deleted
                        from $schema.tab_runtime_assignment
                        where runtime_id = $1 and deleted = false
                        order by id asc
                        """,
                Collections.singletonList(runtimeId),
                "Runtime assignment",
                runtimeAssignmentModelMapper::fromRow);
    }
}
