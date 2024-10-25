package com.omgservers.service.module.runtime.impl.operation.runtimePoolContainerRef;

import com.omgservers.schema.model.runtimePoolContainerRef.RuntimePoolContainerRefModel;
import com.omgservers.service.module.runtime.impl.mapper.RuntimePoolContainerRefModelMapper;
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
class SelectRuntimePoolContainerRefByRuntimeIdOperationImpl implements
        SelectRuntimePoolContainerRefByRuntimeIdOperation {

    final SelectObjectOperation selectObjectOperation;

    final RuntimePoolContainerRefModelMapper runtimePoolContainerRefModelMapper;


    @Override
    public Uni<RuntimePoolContainerRefModel> execute(
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
                        from $schema.tab_runtime_pool_container_ref
                        where runtime_id = $1
                        order by id desc
                        limit 1
                        """,
                List.of(runtimeId),
                "Runtime pool server container ref",
                runtimePoolContainerRefModelMapper::execute);
    }
}