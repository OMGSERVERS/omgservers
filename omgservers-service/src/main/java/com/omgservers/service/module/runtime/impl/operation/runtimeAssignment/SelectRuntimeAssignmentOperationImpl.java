package com.omgservers.service.module.runtime.impl.operation.runtimeAssignment;

import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
import com.omgservers.service.module.runtime.impl.mapper.RuntimeAssignmentModelMapper;
import com.omgservers.service.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectRuntimeAssignmentOperationImpl implements SelectRuntimeAssignmentOperation {

    final SelectObjectOperation selectObjectOperation;

    final RuntimeAssignmentModelMapper runtimeAssignmentModelMapper;

    @Override
    public Uni<RuntimeAssignmentModel> execute(final SqlConnection sqlConnection,
                                               final int shard,
                                               final Long runtimeId,
                                               final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, runtime_id, created, modified, client_id, last_activity, config, deleted
                        from $schema.tab_runtime_assignment
                        where runtime_id = $1 and id = $2
                        limit 1
                        """,
                List.of(
                        runtimeId,
                        id
                ),
                "Runtime assignment",
                runtimeAssignmentModelMapper::execute);
    }
}
