package com.omgservers.service.module.runtime.impl.operation.selectActiveRuntimeClientsByRuntimeId;

import com.omgservers.model.runtimeClient.RuntimeClientModel;
import com.omgservers.service.module.runtime.impl.mapper.RuntimeClientModelMapper;
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
class SelectActiveRuntimeClientsByRuntimeIdOperationImpl implements
        SelectActiveRuntimeClientsByRuntimeIdOperation {

    final SelectListOperation selectListOperation;

    final RuntimeClientModelMapper runtimeClientModelMapper;

    @Override
    public Uni<List<RuntimeClientModel>> selectActiveRuntimeClientsByRuntimeId(final SqlConnection sqlConnection,
                                                                               final int shard,
                                                                               final Long runtimeId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, runtime_id, created, modified, client_id, last_activity, config, deleted
                        from $schema.tab_runtime_client
                        where runtime_id = $1 and deleted = false
                        order by id asc
                        """,
                Collections.singletonList(runtimeId),
                "Runtime client",
                runtimeClientModelMapper::fromRow);
    }
}
