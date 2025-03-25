package com.omgservers.service.shard.runtime.impl.operation.runtimeAssignment;

import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
import com.omgservers.service.shard.runtime.impl.mapper.RuntimeAssignmentModelMapper;
import com.omgservers.service.operation.server.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectRuntimeAssignmentByRuntimeIdAndClientIdOperationImpl implements
        SelectRuntimeAssignmentByRuntimeIdAndClientIdOperation {

    final SelectObjectOperation selectObjectOperation;

    final RuntimeAssignmentModelMapper runtimeAssignmentModelMapper;

    @Override
    public Uni<RuntimeAssignmentModel> execute(final SqlConnection sqlConnection,
                                               final int shard,
                                               final Long runtimeId,
                                               final Long clientId) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, runtime_id, created, modified, client_id, config, deleted
                        from $schema.tab_runtime_assignment
                        where runtime_id = $1 and client_id = $2
                        order by id desc
                        limit 1
                        """,
                List.of(runtimeId, clientId),
                "Runtime assignment",
                runtimeAssignmentModelMapper::execute);
    }
}
