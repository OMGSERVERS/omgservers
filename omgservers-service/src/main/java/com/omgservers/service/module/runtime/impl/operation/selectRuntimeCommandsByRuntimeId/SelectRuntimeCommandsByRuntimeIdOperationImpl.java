package com.omgservers.service.module.runtime.impl.operation.selectRuntimeCommandsByRuntimeId;

import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.service.module.runtime.impl.mapper.RuntimeCommandModelMapper;
import com.omgservers.service.operation.selectList.SelectListOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectRuntimeCommandsByRuntimeIdOperationImpl
        implements SelectRuntimeCommandsByRuntimeIdOperation {

    final SelectListOperation selectListOperation;

    final RuntimeCommandModelMapper runtimeCommandModelMapper;

    @Override
    public Uni<List<RuntimeCommandModel>> selectRuntimeCommandsByRuntimeId(final SqlConnection sqlConnection,
                                                                           final int shard,
                                                                           final Long runtimeId,
                                                                           final Boolean deleted) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select id, runtime_id, created, modified, qualifier, body, deleted
                        from $schema.tab_runtime_command
                        where runtime_id = $1 and deleted = $2
                        order by id asc
                        """,
                Arrays.asList(runtimeId, deleted),
                "Runtime command",
                runtimeCommandModelMapper::fromRow);
    }
}
