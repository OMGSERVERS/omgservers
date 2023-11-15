package com.omgservers.service.module.runtime.impl.operation.selectActiveRuntimeGrantsByRuntimeIdAndEntityIds;

import com.omgservers.model.runtimeGrant.RuntimeGrantModel;
import com.omgservers.service.module.runtime.impl.mapper.RuntimeGrantModelMapper;
import com.omgservers.service.operation.selectList.SelectListOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectActiveRuntimeGrantsByRuntimeIdAndEntityIdsOperationImpl implements
        SelectActiveRuntimeGrantsByRuntimeIdAndEntityIdsOperation {

    final SelectListOperation selectListOperation;

    final RuntimeGrantModelMapper runtimeGrantModelMapper;

    public Uni<List<RuntimeGrantModel>> selectActiveRuntimeGrantsByRuntimeIdAndEntityIds(
            final SqlConnection sqlConnection,
            final int shard,
            final Long runtimeId,
            final List<Long> entityIds) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select id, runtime_id, created, modified, shard_key, entity_id, type, deleted
                        from $schema.tab_runtime_grant
                        where runtime_id = $1 and entity_id = any($2) and deleted = false
                        """,
                Arrays.asList(runtimeId, entityIds.toArray()),
                "Runtime grant",
                runtimeGrantModelMapper::fromRow);
    }
}