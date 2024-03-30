package com.omgservers.service.module.runtime.impl.operation.runtimeServerContainerRef.selectRuntimeServerContainerRef;

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
class SelectRuntimeServerContainerRefOperationImpl implements SelectRuntimeServerContainerRefOperation {

    final SelectObjectOperation selectObjectOperation;

    final RuntimeServerContainerRefModelMapper runtimeServerContainerRefModelMapper;

    @Override
    public Uni<RuntimeServerContainerRefModel> selectRuntimeServerContainerRef(final SqlConnection sqlConnection,
                                                                               final int shard,
                                                                               final Long runtimeId,
                                                                               final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, runtime_id, created, modified, server_id, server_container_id,
                            deleted
                        from $schema.tab_runtime_server_container_ref
                        where runtime_id = $1 and id = $2
                        limit 1
                        """,
                List.of(
                        runtimeId,
                        id
                ),
                "Runtime server container ref",
                runtimeServerContainerRefModelMapper::fromRow);
    }
}
