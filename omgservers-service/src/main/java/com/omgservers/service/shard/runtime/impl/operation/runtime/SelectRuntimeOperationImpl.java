package com.omgservers.service.shard.runtime.impl.operation.runtime;

import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.service.operation.server.SelectObjectOperation;
import com.omgservers.service.shard.runtime.impl.mapper.RuntimeModelMapper;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectRuntimeOperationImpl implements SelectRuntimeOperation {

    final SelectObjectOperation selectObjectOperation;

    final RuntimeModelMapper runtimeModelMapper;

    @Override
    public Uni<RuntimeModel> execute(final SqlConnection sqlConnection,
                                     final int slot,
                                     final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                slot,
                """
                        select
                            id, idempotency_key, created, modified, deployment_id, qualifier, user_id, 
                            config, deleted
                        from $slot.tab_runtime
                        where id = $1
                        limit 1
                        """,
                Collections.singletonList(id),
                "Runtime",
                runtimeModelMapper::execute);
    }
}
