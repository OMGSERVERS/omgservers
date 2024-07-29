package com.omgservers.service.module.runtime.impl.operation.runtimePoolServerContainerRef.selectRuntimePoolServerContainerRef;

import com.omgservers.schema.model.runtimePoolServerContainerRef.RuntimePoolServerContainerRefModel;
import com.omgservers.service.module.runtime.impl.mapper.RuntimePoolServerContainerRefModelMapper;
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
class SelectRuntimePoolServerContainerRefOperationImpl implements SelectRuntimePoolServerContainerRefOperation {

    final SelectObjectOperation selectObjectOperation;

    final RuntimePoolServerContainerRefModelMapper runtimePoolServerContainerRefModelMapper;

    @Override
    public Uni<RuntimePoolServerContainerRefModel> selectRuntimePoolServerContainerRef(
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
                        from $schema.tab_runtime_pool_server_container_ref
                        where runtime_id = $1 and id = $2
                        limit 1
                        """,
                List.of(
                        runtimeId,
                        id
                ),
                "Runtime pool server container ref",
                runtimePoolServerContainerRefModelMapper::fromRow);
    }
}
