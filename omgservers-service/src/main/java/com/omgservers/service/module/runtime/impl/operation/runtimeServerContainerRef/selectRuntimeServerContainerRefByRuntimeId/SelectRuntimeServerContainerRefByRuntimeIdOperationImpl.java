package com.omgservers.service.module.runtime.impl.operation.runtimeServerContainerRef.selectRuntimeServerContainerRefByRuntimeId;

import com.omgservers.model.runtimeServerContainerRef.RuntimeServerContainerRefModel;
import com.omgservers.service.module.runtime.impl.mapper.RuntimeServerContainerRefModelMapper;
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
class SelectRuntimeServerContainerRefByRuntimeIdOperationImpl implements
        SelectRuntimeServerContainerRefByRuntimeIdOperation {

    final SelectObjectOperation selectObjectOperation;

    final RuntimeServerContainerRefModelMapper runtimeServerContainerRefModelMapper;


    @Override
    public Uni<RuntimeServerContainerRefModel> selectRuntimeServerContainerRefByRuntimeId(
            final SqlConnection sqlConnection,
            final int shard,
            final Long runtimeId) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, runtime_id, created, modified, server_id, server_container_id,
                            deleted
                        from $schema.tab_runtime_server_container_ref
                        where runtime_id = $1
                        order by id desc
                        limit 1
                        """,
                List.of(runtimeId),
                "Runtime server container ref",
                runtimeServerContainerRefModelMapper::fromRow);
    }
}
