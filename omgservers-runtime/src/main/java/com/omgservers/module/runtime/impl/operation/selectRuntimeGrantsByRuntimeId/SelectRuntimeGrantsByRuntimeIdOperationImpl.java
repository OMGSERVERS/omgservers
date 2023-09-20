package com.omgservers.module.runtime.impl.operation.selectRuntimeGrantsByRuntimeId;

import com.omgservers.model.runtimeGrant.RuntimeGrantModel;
import com.omgservers.module.runtime.impl.mapper.RuntimeGrantModelMapper;
import com.omgservers.operation.executeSelectList.ExecuteSelectListOperation;
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
class SelectRuntimeGrantsByRuntimeIdOperationImpl implements
        SelectRuntimeGrantsByRuntimeIdOperation {

    final ExecuteSelectListOperation executeSelectListOperation;

    final RuntimeGrantModelMapper runtimeGrantModelMapper;

    @Override
    public Uni<List<RuntimeGrantModel>> selectRuntimeGrantsByRuntimeId(final SqlConnection sqlConnection,
                                                                       final int shard,
                                                                       final Long runtimeId) {
        return executeSelectListOperation.executeSelectList(
                sqlConnection,
                shard,
                """
                        select id, runtime_id, created, modified, shard_key, entity_id, type
                        from $schema.tab_runtime_grant
                        where runtime_id = $1
                        """,
                Collections.singletonList(runtimeId),
                "Runtime grant",
                runtimeGrantModelMapper::fromRow);
    }
}
