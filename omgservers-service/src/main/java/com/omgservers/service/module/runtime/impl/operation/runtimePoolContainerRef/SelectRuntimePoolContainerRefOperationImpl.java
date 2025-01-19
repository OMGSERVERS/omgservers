package com.omgservers.service.module.runtime.impl.operation.runtimePoolContainerRef;

import com.omgservers.schema.model.runtimePoolContainerRef.RuntimePoolContainerRefModel;
import com.omgservers.service.module.runtime.impl.mapper.RuntimePoolContainerRefModelMapper;
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
class SelectRuntimePoolContainerRefOperationImpl implements SelectRuntimePoolContainerRefOperation {

    final SelectObjectOperation selectObjectOperation;

    final RuntimePoolContainerRefModelMapper runtimePoolContainerRefModelMapper;

    @Override
    public Uni<RuntimePoolContainerRefModel> execute(
            final SqlConnection sqlConnection,
            final int shard,
            final Long runtimeId,
            final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, runtime_id, created, modified, pool_id, server_id, container_id, 
                            deleted
                        from $schema.tab_runtime_pool_container_ref
                        where runtime_id = $1 and id = $2
                        limit 1
                        """,
                List.of(
                        runtimeId,
                        id
                ),
                "Runtime pool server container ref",
                runtimePoolContainerRefModelMapper::execute);
    }
}
