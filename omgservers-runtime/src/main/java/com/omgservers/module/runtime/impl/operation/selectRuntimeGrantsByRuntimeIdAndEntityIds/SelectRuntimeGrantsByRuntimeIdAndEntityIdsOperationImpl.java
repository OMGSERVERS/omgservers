package com.omgservers.module.runtime.impl.operation.selectRuntimeGrantsByRuntimeIdAndEntityIds;

import com.omgservers.model.runtimeGrant.RuntimeGrantModel;
import com.omgservers.module.runtime.impl.mapper.RuntimeGrantModelMapper;
import com.omgservers.operation.executeSelectList.ExecuteSelectListOperation;
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
class SelectRuntimeGrantsByRuntimeIdAndEntityIdsOperationImpl implements
        SelectRuntimeGrantsByRuntimeIdAndEntityIdsOperation {

    final ExecuteSelectListOperation executeSelectListOperation;

    final RuntimeGrantModelMapper runtimeGrantModelMapper;

    @Override
    public Uni<List<RuntimeGrantModel>> selectRuntimeGrantsByRuntimeIdAndEntityIds(final SqlConnection sqlConnection,
                                                                                   final int shard,
                                                                                   final Long runtimeId,
                                                                                   final List<Long> entityIds) {
        return executeSelectListOperation.executeSelectList(
                sqlConnection,
                shard,
                """
                        select id, runtime_id, created, modified, shard_key, entity_id, type
                        from $schema.tab_runtime_grant
                        where runtime_id = $1 and entity_id = any($2)
                        """,
                Arrays.asList(runtimeId, entityIds.toArray()),
                "Runtime grant",
                runtimeGrantModelMapper::fromRow);
    }
}
