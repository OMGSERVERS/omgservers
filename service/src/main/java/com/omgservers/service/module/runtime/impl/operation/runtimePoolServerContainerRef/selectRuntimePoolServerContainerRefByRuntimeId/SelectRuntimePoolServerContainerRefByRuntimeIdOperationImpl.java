package com.omgservers.service.module.runtime.impl.operation.runtimePoolServerContainerRef.selectRuntimePoolServerContainerRefByRuntimeId;

import com.omgservers.schema.model.runtimePoolServerContainerRef.RuntimePoolServerContainerRefModel;
import com.omgservers.service.module.runtime.impl.mapper.RuntimePoolServerContainerRefModelMapper;
import com.omgservers.service.server.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectRuntimePoolServerContainerRefByRuntimeIdOperationImpl implements
        SelectRuntimePoolServerContainerRefByRuntimeIdOperation {

    final SelectObjectOperation selectObjectOperation;

    final RuntimePoolServerContainerRefModelMapper runtimePoolServerContainerRefModelMapper;


    @Override
    public Uni<RuntimePoolServerContainerRefModel> selectRuntimePoolServerContainerRefByRuntimeId(
            final SqlConnection sqlConnection,
            final int shard,
            final Long runtimeId) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, runtime_id, created, modified, pool_id, server_id, container_id,
                            deleted
                        from $schema.tab_runtime_pool_server_container_ref
                        where runtime_id = $1
                        order by id desc
                        limit 1
                        """,
                List.of(runtimeId),
                "Runtime pool server container ref",
                runtimePoolServerContainerRefModelMapper::fromRow);
    }
}
