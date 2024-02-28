package com.omgservers.service.module.runtime.impl.operation.selectRuntimeClient;

import com.omgservers.model.runtimeClient.RuntimeClientModel;
import com.omgservers.service.module.runtime.impl.mapper.RuntimeClientModelMapper;
import com.omgservers.service.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectRuntimeClientOperationImpl implements SelectRuntimeClientOperation {

    final SelectObjectOperation selectObjectOperation;

    final RuntimeClientModelMapper runtimeClientModelMapper;

    @Override
    public Uni<RuntimeClientModel> selectRuntimeClient(final SqlConnection sqlConnection,
                                                       final int shard,
                                                       final Long runtimeId,
                                                       final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, runtime_id, created, modified, client_id, last_activity, config, deleted
                        from $schema.tab_runtime_client
                        where runtime_id = $1 and id = $2
                        limit 1
                        """,
                Arrays.asList(
                        runtimeId,
                        id
                ),
                "Runtime client",
                runtimeClientModelMapper::fromRow);
    }
}
