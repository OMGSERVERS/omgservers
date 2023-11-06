package com.omgservers.service.module.runtime.impl.operation.selectRuntimeGrantsByRuntimeId;

import com.omgservers.model.runtimeGrant.RuntimeGrantModel;
import com.omgservers.service.module.runtime.impl.mapper.RuntimeGrantModelMapper;
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
class SelectRuntimeGrantsByRuntimeIdOperationImpl implements
        SelectRuntimeGrantsByRuntimeIdOperation {

    final SelectListOperation selectListOperation;

    final RuntimeGrantModelMapper runtimeGrantModelMapper;

    @Override
    public Uni<List<RuntimeGrantModel>> selectRuntimeGrantsByRuntimeId(final SqlConnection sqlConnection,
                                                                       final int shard,
                                                                       final Long runtimeId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select id, runtime_id, created, modified, shard_key, entity_id, type, deleted
                        from $schema.tab_runtime_grant
                        where runtime_id = $1 and deleted = false
                        """,
                Collections.singletonList(runtimeId),
                "Runtime grant",
                runtimeGrantModelMapper::fromRow);
    }
}
