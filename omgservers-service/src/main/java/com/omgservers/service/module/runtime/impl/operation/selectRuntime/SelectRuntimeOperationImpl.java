package com.omgservers.service.module.runtime.impl.operation.selectRuntime;

import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.service.module.runtime.impl.mapper.RuntimeModelMapper;
import com.omgservers.service.operation.selectObject.SelectObjectOperation;
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
    public Uni<RuntimeModel> selectRuntime(final SqlConnection sqlConnection,
                                           final int shard,
                                           final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, created, modified, tenant_id, version_id, type, config, deleted
                        from $schema.tab_runtime
                        where id = $1
                        limit 1
                        """,
                Collections.singletonList(id),
                "Runtime",
                runtimeModelMapper::fromRow);
    }
}
