package com.omgservers.service.module.runtime.impl.operation.runtimeCommand;

import com.omgservers.schema.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.service.module.runtime.impl.mapper.RuntimeCommandModelMapper;
import com.omgservers.service.operation.selectList.SelectListOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectActiveRuntimeCommandsByRuntimeIdOperationImpl
        implements SelectActiveRuntimeCommandsByRuntimeIdOperation {

    final SelectListOperation selectListOperation;

    final RuntimeCommandModelMapper runtimeCommandModelMapper;

    @Override
    public Uni<List<RuntimeCommandModel>> execute(final SqlConnection sqlConnection,
                                                  final int shard,
                                                  final Long runtimeId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, runtime_id, created, modified, qualifier, body, deleted
                        from $schema.tab_runtime_command
                        where runtime_id = $1 and deleted = false
                        order by id asc
                        """,
                Collections.singletonList(runtimeId),
                "Runtime command",
                runtimeCommandModelMapper::execute);
    }
}
