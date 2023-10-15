package com.omgservers.module.runtime.impl.operation.selectRuntime;

import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.module.runtime.impl.mapper.RuntimeMapper;
import com.omgservers.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectRuntimeOperationImpl implements SelectRuntimeOperation {

    final SelectObjectOperation selectObjectOperation;

    final RuntimeMapper runtimeMapper;

    @Override
    public Uni<RuntimeModel> selectRuntime(final SqlConnection sqlConnection,
                                           final int shard,
                                           final Long id,
                                           final Boolean deleted) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, created, modified, tenant_id, stage_id, version_id, matchmaker_id, match_id,
                            type, step, script_id, config, deleted
                        from $schema.tab_runtime
                        where id = $1 and deleted = $2
                        limit 1
                        """,
                Arrays.asList(id, deleted),
                "Runtime",
                runtimeMapper::fromRow);
    }
}
